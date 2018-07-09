package com.joinpay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;

import com.joinpay.common.BaseController;
import com.joinpay.common.Constant;
import com.joinpay.common.SysResponse;
import com.joinpay.common.Tree;
import com.joinpay.entity.SysDept;
import com.joinpay.entity.SysRole;
import com.joinpay.entity.SysUser;
import com.joinpay.enums.Code;
import com.joinpay.service.RoleService;
import com.joinpay.service.UserService;
import com.joinpay.util.MD5Utils;
import com.joinpay.util.PageUtils;
import com.joinpay.util.Query;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {
	private String prefix = "system/user";
	@Autowired
	UserService userService;
	// @Autowired
	// RedisCache redisCache;
	@Autowired
	RoleService roleService;
	// @Autowired
	// DictService dictService;

	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return prefix + "/user";
	}

	@GetMapping("{id}")
	public SysResponse hello(@PathVariable Long id) {
		SysUser user = userService.get(id);
		// redisUtil.set(user.getName(), user);

		return SysResponse.OK(user);
	}


	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<SysUser> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@RequiresPermissions("sys:user:add")
	@GetMapping("/add")
	String add(Model model) {
		List<SysRole> roles = roleService.list();
		model.addAttribute("roles", roles);
		return prefix + "/add";
	}

	@RequiresPermissions("sys:user:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		SysUser userDO = userService.get(id);
		model.addAttribute("user", userDO);
		List<SysRole> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return prefix + "/edit";
	}

	@RequiresPermissions("sys:user:add")
	@PostMapping("/save")
	@ResponseBody
	SysResponse save(SysUser user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		if (userService.save(user) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	@RequiresPermissions("sys:user:edit")
	@PostMapping("/update")
	@ResponseBody
	SysResponse update(SysUser user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.update(user) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	@RequiresPermissions("sys:user:edit")
	@PostMapping("/updatePeronal")
	@ResponseBody
	SysResponse updatePeronal(SysUser user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.updatePersonal(user) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	@RequiresPermissions("sys:user:remove")
	@PostMapping("/remove")
	@ResponseBody
	SysResponse remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.remove(id) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param userIds
	 * @return
	 */
	@RequiresPermissions("sys:user:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	SysResponse batchRemove(@RequestParam("ids[]") Long[] userIds) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	/**
	 * 请求更改用户密码
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:resetPwd")

	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {

		SysUser userDO = new SysUser();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return prefix + "/reset_pwd";
	}

	/**
	 * 提交更改用户密码
	 * 
	 * @param userVO
	 * @return
	 */
	@PostMapping("/resetPwd")
	@ResponseBody
	SysResponse resetPwd(SysUser userVO) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		try {
			userService.resetPwd(userVO, getUser());
			return SysResponse.OK();
		} catch (Exception e) {

			return SysResponse.create(Code.SystemError, e.getMessage());
		}

	}

	/**
	 * admin提交更改用户密码
	 * 
	 * @param userVO
	 * @return
	 */
	@RequiresPermissions("sys:user:resetPwd")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	SysResponse adminResetPwd(SysUser userVO) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		try {
			userService.adminResetPwd(userVO);
			return SysResponse.OK();
		} catch (Exception e) {
			return SysResponse.create(Code.SystemError, e.getMessage());
		}

	}

	@GetMapping("/tree")
	@ResponseBody
	public Tree<SysDept> tree() {
		Tree<SysDept> tree = new Tree<SysDept>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return prefix + "/userTree";
	}

	// @GetMapping("/personal")
	// String personal(Model model) {
	// SysUser userDO = userService.get(getUserId());
	// model.addAttribute("user",userDO);
	// model.addAttribute("hobbyList",dictService.getHobbyList(userDO));
	// model.addAttribute("sexList",dictService.getSexList());
	// return prefix + "/personal";
	// }

	@ResponseBody
	@PostMapping("/uploadImg")
	SysResponse uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data,
			HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> result = new HashMap<>();
		try {
			result = userService.updatePersonalImg(file, avatar_data, getUserId());
		} catch (Exception e) {
			return SysResponse.create(Code.SystemError, "更新图像失败！");
		}
		if (result != null && result.size() > 0) {
			return SysResponse.OK(result);
		} else {
			return SysResponse.create(Code.SystemError, "更新图像失败！");
		}
	}
}
