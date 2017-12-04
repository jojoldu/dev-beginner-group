package com.jojoldu.beginner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
public class WebApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "/app/config/real-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.listeners(new ApplicationPidFileWriter())
				.run(args);

	}
}
