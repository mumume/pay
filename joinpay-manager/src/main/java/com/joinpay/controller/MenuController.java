package com.joinpay.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joinpay.common.BaseController;
import com.joinpay.common.Constant;
import com.joinpay.common.SysResponse;
import com.joinpay.common.Tree;
import com.joinpay.entity.SysMenu;
import com.joinpay.enums.Code;
import com.joinpay.service.MenuService;

@RequestMapping("/sys/menu")
@Controller
public class MenuController extends BaseController {
	String prefix = "system/menu";
	@Autowired
	MenuService menuService;

	@RequiresPermissions("sys:menu:menu")
	@GetMapping()
	String menu(Model model) {
		return prefix + "/menu";
	}

	@RequiresPermissions("sys:menu:menu")
	@RequestMapping("/list")
	@ResponseBody
	List<SysMenu> list(@RequestParam Map<String, Object> params) {
		List<SysMenu> menus = menuService.list(params);
		return menus;
	}

	/**
	 * 添加菜单
	 * 
	 * @param model
	 * @param pId
	 * @return
	 */
	@RequiresPermissions("sys:menu:add")
	@GetMapping("/add/{pId}")
	String add(Model model, @PathVariable("pId") Long pId) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		return prefix + "/add";
	}

	/**
	 * 编辑菜单
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:menu:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		SysMenu mdo = menuService.get(id);
		Long pId = mdo.getParentId();
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		model.addAttribute("menu", mdo);
		return prefix + "/edit";
	}

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("sys:menu:add")
	@PostMapping("/save")
	@ResponseBody
	SysResponse save(SysMenu menu) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.save(menu) > 0) {
			return SysResponse.OK();
		} else {
			return SysResponse.create(Code.SystemError);
		}
	}

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequiresPermissions("sys:menu:edit")
	@PostMapping("/update")
	@ResponseBody
	SysResponse update(SysMenu menu) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.update(menu) > 0) {
			return SysResponse.OK();
		} else {
			return SysResponse.create(Code.SystemError, "更新失败");
		}
	}

	/**
	 * 删除菜单
	 */
	@RequiresPermissions("sys:menu:remove")
	@PostMapping("/remove")
	@ResponseBody
	SysResponse remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (menuService.remove(id) > 0) {
			return SysResponse.OK();
		} else {
			return SysResponse.create(Code.SystemError, "删除失败");
		}
	}

	@GetMapping("/tree")
	@ResponseBody
	Tree<SysMenu> tree() {
		Tree<SysMenu> tree = menuService.getTree();
		return tree;
	}

	@GetMapping("/tree/{roleId}")
	@ResponseBody
	Tree<SysMenu> tree(@PathVariable("roleId") Long roleId) {
		Tree<SysMenu> tree = menuService.getTree(roleId);
		return tree;
	}
}
