package com.joinpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joinpay.common.SysResponse;
import com.joinpay.entity.SysUser;
import com.joinpay.redis.RedisCache;
import com.joinpay.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	RedisCache redisCache;

	@GetMapping("{id}")
	public SysResponse hello(@PathVariable Long id) {
		SysUser user = userService.get(id);
//		redisUtil.set(user.getName(), user);
		return SysResponse.OK(user);
	}

	@GetMapping("list")
	public SysResponse list() {
		List<SysUser> users = userService.list();
		return SysResponse.OK(users);
	}

}
