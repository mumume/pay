package com.joinpay.dao.self;

import java.util.List;

import com.joinpay.entity.SysMenu;

public interface MenuMapper {

	List<String> listUserPerms(Long userId);

	List<SysMenu> listMenuByUserId(Long userId);
	
	

}
