package com.joinpay.service;

import java.util.List;

import com.joinpay.entity.SysUser;

public interface UserService {

	SysUser get(Long id);

	List<SysUser> list();

}
