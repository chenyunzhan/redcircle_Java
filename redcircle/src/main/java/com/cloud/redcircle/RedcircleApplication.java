package com.cloud.redcircle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class RedcircleApplication {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(RedcircleApplication.class, args);
	}
}
