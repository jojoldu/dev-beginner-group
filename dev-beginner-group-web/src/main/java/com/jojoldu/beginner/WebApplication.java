package com.jojoldu.beginner;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class WebApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "/app/config/dev-beginner-group-common/real-db-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(WebApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

	@Bean
	@Profile("local")
	@ConfigurationProperties("spring.datasource") // yml의 설정값을 Set한다.
	public DataSource dataSource() throws SQLException {
		Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093").start();
		return new HikariDataSource();
	}
}
