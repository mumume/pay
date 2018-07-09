package com.joinpay.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joinpay.common.BaseController;
import com.joinpay.common.SysResponse;
import com.joinpay.common.Tree;
import com.joinpay.entity.SysFile;
import com.joinpay.entity.SysMenu;
import com.joinpay.enums.Code;
import com.joinpay.service.FileService;
import com.joinpay.service.MenuService;
import com.joinpay.util.MD5Utils;
import com.joinpay.util.ShiroUtils;

@Controller
public class LoginController extends BaseController {

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;

	@GetMapping({ "/", "" })
	String welcome(Model model) {
		return "redirect:/blog";
	}

	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<SysMenu>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		SysFile SysFile = fileService.get(getUser().getPicId());
		if (SysFile != null && SysFile.getUrl() != null) {
			if (fileService.isExist(SysFile.getUrl())) {
				model.addAttribute("picUrl", SysFile.getUrl());
			} else {
				model.addAttribute("picUrl", "/img/photo_s.jpg");
			}
		} else {
			model.addAttribute("picUrl", "/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	SysResponse ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return SysResponse.OK();
		} catch (AuthenticationException e) {
			return SysResponse.create(Code.PassWrong, "用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

}
