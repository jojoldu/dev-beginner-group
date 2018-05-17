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

    public void send(SenderDto senderDto){
        try {
            log.info("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            AWSCredentialsProviderChain chain = new AWSCredentialsProviderChain(
                    new ProfileCredentialsProvider(),
                    new SystemPropertiesCredentialsProvider(),
                    new EnvironmentVariableCredentialsProvider(),
                    InstanceProfileCredentialsProvider.getInstance()
            );

            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(chain)
                    .withRegion("us-west-2")
                    .build();

            // Send the email.
            senderDto.toSendRequestDtos()
                    .forEach(client::sendEmail);

            log.info("Email sent!");

        } catch (Exception ex) {
            log.error("The email was not sent.: "+senderDto.getTo().get(0));
            log.error("Error message: " + ex.getMessage());
            throw new AmazonClientException(
                    ex.getMessage(),
                    ex);
        }
    }

}
