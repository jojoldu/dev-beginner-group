package com.jojoldu.beginner.mail.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by jojoldu@gmail.com on 2017. 11. 10.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Component
@Slf4j
public class Sender {

    public void send(SenderDto senderDto){
        try {
            log.info("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();

            try {
                credentialsProvider.getCredentials();
            } catch (Exception e) {
                throw new AmazonClientException(
                        "Cannot load the credentials from the credential profiles file. " +
                                "Please make sure that your credentials file is at the correct " +
                                "location (~/.aws/credentials), and is in valid format.",
                        e);
            }

            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(credentialsProvider)
                    .withRegion("us-west-2")
                    .build();

            // Send the email.
            client.sendEmail(senderDto.toSendRequestDto());
            log.info("Email sent!");

        } catch (Exception ex) {
            log.error("The email was not sent.");
            log.error("Error message: " + ex.getMessage());
            throw new AmazonClientException(
                    ex.getMessage(),
                    ex);
        }
    }
}
