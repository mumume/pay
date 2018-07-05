package com.joinpay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joinpay.dao.generator.SysUserMapper;
import com.joinpay.dao.self.UserMapper;
import com.joinpay.entity.SysUser;
import com.joinpay.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	SysUserMapper sysUserMapper;
	@Autowired
	UserMapper userMapper;

	@Override
	public SysUser get(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysUser> list() {
		return userMapper.list();
	}

}
