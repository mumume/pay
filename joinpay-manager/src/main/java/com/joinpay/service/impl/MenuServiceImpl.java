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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joinpay.common.BuildTree;
import com.joinpay.common.Tree;
import com.joinpay.dao.self.MenuMapper;
import com.joinpay.dao.self.RoleMenuMapper;
import com.joinpay.entity.SysMenu;
import com.joinpay.service.MenuService;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuMapper menuMapper;
	@Autowired
	RoleMenuMapper roleMenuMapper;

	/**
	 * @param
	 * @return 树形菜单
	 */
	@Cacheable
	@Override
	public Tree<SysMenu> getSysMenuTree(Long id) {
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> menuDOs = menuMapper.listMenuByUserId(id);
		for (SysMenu sysSysMenu : menuDOs) {
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
		Tree<SysMenu> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public List<SysMenu> list(Map<String, Object> params) {
		List<SysMenu> menus = menuMapper.list(params);
		return menus;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int remove(Long id) {
		int result = menuMapper.remove(id);
		return result;
	}
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int save(SysMenu menu) {
		int r = menuMapper.save(menu);
		return r;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public int update(SysMenu menu) {
		int r = menuMapper.update(menu);
		return r;
	}

	@Override
	public SysMenu get(Long id) {
		SysMenu menuDO = menuMapper.get(id);
		return menuDO;
	}

	@Override
	public Tree<SysMenu> getTree() {
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> menuDOs = menuMapper.list(new HashMap<>(16));
		for (SysMenu sysSysMenu : menuDOs) {
			Tree<SysMenu> tree = new Tree<SysMenu>();
			tree.setId(sysSysMenu.getMenuId().toString());
			tree.setParentId(sysSysMenu.getParentId().toString());
			tree.setText(sysSysMenu.getName());
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<SysMenu> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public Tree<SysMenu> getTree(Long id) {
		// 根据roleId查询权限
		List<SysMenu> menus = menuMapper.list(new HashMap<String, Object>(16));
		List<Long> menuIds = roleMenuMapper.listMenuIdByRoleId(id);
		List<Long> temp = menuIds;
		for (SysMenu menu : menus) {
			if (temp.contains(menu.getParentId())) {
				menuIds.remove(menu.getParentId());
			}
		}
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> menuDOs = menuMapper.list(new HashMap<String, Object>(16));
		for (SysMenu sysSysMenu : menuDOs) {
			Tree<SysMenu> tree = new Tree<SysMenu>();
			tree.setId(sysSysMenu.getMenuId().toString());
			tree.setParentId(sysSysMenu.getParentId().toString());
			tree.setText(sysSysMenu.getName());
			Map<String, Object> state = new HashMap<>(16);
			Long menuId = sysSysMenu.getMenuId();
			if (menuIds.contains(menuId)) {
				state.put("selected", true);
			} else {
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<SysMenu> t = BuildTree.build(trees);
		return t;
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

	@Override
	public List<Tree<SysMenu>> listMenuTree(Long id) {
		List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
		List<SysMenu> menuDOs = menuMapper.listMenuByUserId(id);
		for (SysMenu sysSysMenu : menuDOs) {
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


}
