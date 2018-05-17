package com.jojoldu.beginner.mail.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 10.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
public class Sender {

    public void send(SenderDto senderDto) {
        try {
            log.info("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(getCredentialsChain())
                    .withRegion("us-west-2")
                    .build();

            // Send the email.
            senderDto.toSendRequestDtos()
                    .forEach(client::sendEmail);

            log.info("Email sent!");

        } catch (Exception ex) {
            log.error("The email was not sent.: " + senderDto.getTo().get(0));
            log.error("Error message: " + ex.getMessage());
            throw new AmazonClientException(ex.getMessage(), ex);
        }
    }

    /**
     * 우선순위
     * <p>
     * 1. 자바 시스템 프로퍼티 -Daws.accessKeyId=? -Daws.secretKey=?
     * 2. os 환경변수 AWS_ACCESS_KEY_ID=?, AWS_SECRET_KEY=?
     * 3. ~/.aws/credentials
     * 4. IAM Role (aws configure list)
     */
    private AWSCredentialsProviderChain getCredentialsChain() {
        return new AWSCredentialsProviderChain(
                new SystemPropertiesCredentialsProvider(),
                new EnvironmentVariableCredentialsProvider(),
                new ProfileCredentialsProvider(),
                InstanceProfileCredentialsProvider.getInstance()
        );
    }

}
