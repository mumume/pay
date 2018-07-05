package com.joinpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joinpay.common.HttpResponse;
import com.joinpay.entity.SysUser;
import com.joinpay.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("{id}")
	public HttpResponse hello(@PathVariable Long id) {
		SysUser user = userService.get(id);
		return HttpResponse.OK(user);
	}

	@GetMapping("list")
	public HttpResponse list() {
		List<SysUser> users = userService.list();
		return HttpResponse.OK(users);
	}

}
