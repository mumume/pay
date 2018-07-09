package com.joinpay.common;

import org.springframework.stereotype.Controller;

import com.joinpay.entity.SysUser;
import com.joinpay.util.ShiroUtils;

@Controller
public class BaseController {
	
	public SysUser getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}