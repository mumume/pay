package com.joinpay.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joinpay.common.BuildTree;
import com.joinpay.common.Tree;
import com.joinpay.dao.self.MenuMapper;
import com.joinpay.entity.SysMenu;
import com.joinpay.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuMapper menuMapper;

	@Override
	public List<Tree<SysMenu>> listMenuTree(Long userId) {
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> SysMenus = menuMapper.listMenuByUserId(userId);
		for (SysMenu sysSysMenu : SysMenus) {
			Tree<SysMenu> tree = new Tree<SysMenu>();
			tree.setId(sysSysMenu.getMenuId().toString());
			tree.setParentId(sysSysMenu.getParentId().toString());
			tree.setText(sysSysMenu.getName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", sysSysMenu.getUrl());
			attributes.put("icon", sysSysMenu.getIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<SysMenu>> list = BuildTree.buildList(trees, "0");
		return list;
	}

	@Override
	public Set<String> listPerms(Long userId) {
		List<String> perms = menuMapper.listUserPerms(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StringUtils.isNotBlank(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

}
