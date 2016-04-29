package com.cloud.redcircle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableConfigurationProperties({RedCircleProperties.class})  

public class RedcircleApplication implements CommandLineRunner {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	MessageService  messageService;

	public static void main(String[] args) {
		SpringApplication.run(RedcircleApplication.class, args);
		
	}
	
	
    @Override
    public void run(String... strings) throws Exception {
		messageService.setSyncMessageTimer();
    }
}
