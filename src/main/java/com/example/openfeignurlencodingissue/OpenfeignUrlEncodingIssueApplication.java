package com.example.openfeignurlencodingissue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OpenfeignUrlEncodingIssueApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfeignUrlEncodingIssueApplication.class, args);
	}

}
