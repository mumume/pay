package com.joinpay.controller;

import java.util.List;

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
import com.joinpay.entity.SysRole;
import com.joinpay.enums.Code;
import com.joinpay.service.RoleService;

@RequestMapping("/sys/role")
@Controller
public class RoleController extends BaseController {
	String prefix = "system/role";
	@Autowired
	RoleService roleService;

	@RequiresPermissions("sys:role:role")
	@GetMapping()
	String role() {
		return prefix + "/role";
	}

	@RequiresPermissions("sys:role:role")
	@GetMapping("/list")
	@ResponseBody()
	List<SysRole> list() {
		List<SysRole> roles = roleService.list();
		return roles;
	}

	/**
	 * 添加角色")
	 * 
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}

	/**
	 * 编辑角色")
	 */
	@RequiresPermissions("sys:role:edit")
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Long id, Model model) {
		SysRole SysRole = roleService.get(id);
		model.addAttribute("role", SysRole);
		return prefix + "/edit";
	}

	/**
	 * 保存角色")
	 */
	@RequiresPermissions("sys:role:add")
	@PostMapping("/save")
	@ResponseBody()
	SysResponse save(SysRole role) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.save(role) > 0) {
			return SysResponse.OK();
		} else {
			return SysResponse.create(Code.SystemError, "保存失败");
		}
	}

	/**
	 * 更新角色")
	 */
	@RequiresPermissions("sys:role:edit")
	@PostMapping("/update")
	@ResponseBody()
	SysResponse update(SysRole role) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.update(role) > 0) {
			return SysResponse.OK();
		} else {
			return SysResponse.create(Code.SystemError, "保存失败");
		}
	}

	/**
	 * 删除角色")
	 */
	@RequiresPermissions("sys:role:remove")
	@PostMapping("/remove")
	@ResponseBody()
	SysResponse save(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (roleService.remove(id) > 0) {
			return SysResponse.OK();
		} else {
			return SysResponse.create(Code.SystemError, "删除失败");
		}
	}

	@RequiresPermissions("sys:role:batchRemove")
	/**
	 * 批量删除角色")
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	SysResponse batchRemove(@RequestParam("ids[]") Long[] ids) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		int r = roleService.batchremove(ids);
		if (r > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}
}
