package com.jojoldu.beginner;

import com.jojoldu.beginner.mail.template.HandlebarsFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@SpringBootApplication
@EnableBatchProcessing
@Configuration
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Bean
	public HandlebarsFactory handlebarsFactory() throws IOException {
		HandlebarsFactory factory = new HandlebarsFactory();
		factory.put("WeeklyLetter");
		return factory;
	}
}
