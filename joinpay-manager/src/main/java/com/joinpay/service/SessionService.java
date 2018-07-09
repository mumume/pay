package com.joinpay.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.joinpay.entity.SysUser;
import com.joinpay.entity.UserOnline;

@Service
public interface SessionService {
	List<UserOnline> list();

	List<SysUser> listOnlineUser();

	Collection<Session> sessionList();

	boolean forceLogout(String sessionId);
}
