package com.joinpay.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.joinpay.common.Tree;
import com.joinpay.entity.SysMenu;

public interface MenuService {

	Tree<SysMenu> getSysMenuTree(Long id);

	List<Tree<SysMenu>> listMenuTree(Long id);

	Tree<SysMenu> getTree();

	Tree<SysMenu> getTree(Long id);

	List<SysMenu> list(Map<String, Object> params);

	int remove(Long id);

	int save(SysMenu menu);

	int update(SysMenu menu);

	SysMenu get(Long id);

	Set<String> listPerms(Long userId);
	

}
