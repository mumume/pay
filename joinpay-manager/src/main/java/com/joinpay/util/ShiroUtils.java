package com.joinpay.util;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.joinpay.entity.SysUser;

public class ShiroUtils {
	
	@Autowired
	private static SessionDAO sessionDAO;

	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}

	public static SysUser getUser() {
		Object object = getSubjct().getPrincipal();
		return (SysUser) object;
	}

	public static Long getUserId() {
		return getUser().getUserId();
	}

	public static void logout() {
		getSubjct().logout();
	}

	public static List<Principal> getPrinciples() {
		List<Principal> principals = null;
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		return principals;
	}
}
