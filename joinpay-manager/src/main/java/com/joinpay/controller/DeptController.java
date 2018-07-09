package com.joinpay.controller;

import java.util.HashMap;
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
import com.joinpay.entity.SysDept;
import com.joinpay.enums.Code;
import com.joinpay.service.DeptService;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {
	private String prefix = "system/dept";
	@Autowired
	private DeptService sysDeptService;

	@GetMapping()
	@RequiresPermissions("system:sysDept:sysDept")
	String dept() {
		return prefix + "/dept";
	}

	/**
	 * 获取部门列表
	 * 
	 * @return
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:sysDept:sysDept")
	public List<SysDept> list() {
		Map<String, Object> query = new HashMap<>(16);
		List<SysDept> sysDeptList = sysDeptService.list(query);
		return sysDeptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:sysDept:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总部门");
		} else {
			model.addAttribute("pName", sysDeptService.get(pId).getName());
		}
		return prefix + "/add";
	}

	@GetMapping("/edit/{deptId}")
	@RequiresPermissions("system:sysDept:edit")
	String edit(@PathVariable("deptId") Long deptId, Model model) {
		SysDept sysDept = sysDeptService.get(deptId);
		model.addAttribute("sysDept", sysDept);
		if (Constant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
			model.addAttribute("parentDeptName", "无");
		} else {
			SysDept parDept = sysDeptService.get(sysDept.getParentId());
			model.addAttribute("parentDeptName", parDept.getName());
		}
		return prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sysDept:add")
	public SysResponse save(SysDept sysDept) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (sysDeptService.save(sysDept) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sysDept:edit")
	public SysResponse update(SysDept sysDept) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (sysDeptService.update(sysDept) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:remove")
	public SysResponse remove(Long deptId) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", deptId);
		if (sysDeptService.count(map) > 0) {
			return SysResponse.create(Code.SystemError, "包含下级部门,不允许修改");
		}
		if (sysDeptService.checkDeptHasUser(deptId)) {
			if (sysDeptService.remove(deptId) > 0) {
				return SysResponse.OK();
			}
		} else {
			return SysResponse.create(Code.SystemError, "部门包含用户,不允许修改");
		}
		return SysResponse.create(Code.SystemError);
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:batchRemove")
	public SysResponse remove(@RequestParam("ids[]") Long[] deptIds) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		sysDeptService.batchRemove(deptIds);
		return SysResponse.OK();
	}

	@GetMapping("/tree")
	@ResponseBody
	public Tree<SysDept> tree() {
		Tree<SysDept> tree = new Tree<SysDept>();
		tree = sysDeptService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return prefix + "/deptTree";
	}

}
