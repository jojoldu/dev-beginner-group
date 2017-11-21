package com.jojoldu.staticuploader.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
public class StaticUploader {

    private static final String BUCKET_NAME = "devbeginner.com";

    public void upload(File uploadFile, String fileName) {
        AmazonS3 s3 = getS3Instance(getAwsCredentials());
        putS3(uploadFile, fileName, s3);
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

    private void putS3(File uploadFile, String fileName, AmazonS3 s3) {
        try {
            log.info("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(BUCKET_NAME, fileName, uploadFile));
            log.info("Static File Uploaded!");

        } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
        }
    }
}
