package com.jojoldu.beginner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebApplication {
	public static final String PG_CONFIG_LOCATIONS = "spring.config.location="
			+ "classpath:/application.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(WebApplication.class)
				.properties(PG_CONFIG_LOCATIONS)
				.run(args);
	}
}
