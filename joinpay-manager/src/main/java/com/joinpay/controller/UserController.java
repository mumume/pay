package com.joinpay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/manager/{name}")
	public Object hello(@PathVariable String name) {
		System.out.println("name---" + name);
		return "hello" + name;
	}

	@GetMapping("test")
	public void test() {
		System.out.println("test");
	}
}
