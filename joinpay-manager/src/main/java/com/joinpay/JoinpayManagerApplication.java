package com.joinpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.joinpay.controller")
public class JoinpayManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoinpayManagerApplication.class, args);
	}
}
