package com.jojoldu.beginner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:/application.yml,"
			+ "/var/app/dev-beginner-group-web/real-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);

	}
}
