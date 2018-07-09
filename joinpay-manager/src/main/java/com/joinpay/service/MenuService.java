package com.joinpay.service;

import java.util.List;
import java.util.Set;

import com.joinpay.common.Tree;
import com.joinpay.entity.SysMenu;

public interface MenuService {

	List<Tree<SysMenu>> listMenuTree(Long userId);

	Set<String> listPerms(Long userId);
	

}
