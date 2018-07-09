package com.joinpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan("com.joinpay.controller")
@MapperScan("com.joinpay.dao")
public class JoinpayManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoinpayManagerApplication.class, args);
	}
}
