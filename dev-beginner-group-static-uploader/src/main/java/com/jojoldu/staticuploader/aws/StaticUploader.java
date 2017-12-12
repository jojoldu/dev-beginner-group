package com.jojoldu.staticuploader.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
public class StaticUploader {

    private static final String BUCKET_NAME = "devbeginner.com";

    public String upload(MultipartFile multipartFile) throws IOException {
        return upload(convert(multipartFile), multipartFile.getOriginalFilename());
    }

    public String upload(File uploadFile, String fileName) {
        AmazonS3 s3 = getS3Instance(getAwsCredentials());
        return putS3(uploadFile, fileName, s3);
    }

    private File convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        convertFile.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
        return convertFile;
    }

    private AWSCredentials getAwsCredentials() {
        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        return credentials;
    }

    private AmazonS3 getS3Instance(AWSCredentials credentials) {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion("ap-northeast-2")
                .build();
    }

    private String putS3(File uploadFile, String fileName, AmazonS3 s3) {
        try {
            log.info("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(BUCKET_NAME, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
            log.info("Static File Uploaded!");
            return extractImageUrl(s3, fileName);

        } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
            throw ace;
        }
    }

    private String extractImageUrl(AmazonS3 s3, String fileName){
        URL urlObject = s3.getUrl(BUCKET_NAME, fileName);
        String protocol = urlObject.getProtocol();
        String host = "s3.ap-northeast-2.amazonaws.com";
        String path = urlObject.getPath();

        return protocol+"://"+host+"/"+BUCKET_NAME+path;

    }
}
