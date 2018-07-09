package com.joinpay.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.joinpay.common.BaseController;
import com.joinpay.common.SysResponse;
import com.joinpay.config.JoinpayConfig;
import com.joinpay.entity.SysFile;
import com.joinpay.service.FileService;
import com.joinpay.util.FileType;
import com.joinpay.util.FileUtil;
import com.joinpay.util.PageUtils;
import com.joinpay.util.Query;

/**
 * 文件上传
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-19 16:02:20
 */
@Controller
@RequestMapping("/common/sysFile")
public class FileController extends BaseController {

	@Autowired
	private FileService sysFileService;

	@Autowired
	private JoinpayConfig joinpayConfig;

	@GetMapping()
	@RequiresPermissions("common:sysFile:sysFile")
	String sysFile(Model model) {
		@SuppressWarnings("unused")
		Map<String, Object> params = new HashMap<>(16);
		return "common/file/file";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:sysFile:sysFile")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<SysFile> sysFileList = sysFileService.list(query);
		int total = sysFileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	// @RequiresPermissions("common:bComments")
	String add() {
		return "common/sysFile/add";
	}

	@GetMapping("/edit")
	// @RequiresPermissions("common:bComments")
	String edit(Long id, Model model) {
		SysFile sysFile = sysFileService.get(id);
		model.addAttribute("sysFile", sysFile);
		return "common/sysFile/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("common:info")
	public Object info(@PathVariable("id") Long id) {
		SysFile sysFile = sysFileService.get(id);
		return new HashMap<String, Object>().put("sysFile", sysFile);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:save")
	public SysResponse save(SysFile sysFile) {
		if (sysFileService.save(sysFile) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("common:update")
	public SysResponse update(@RequestBody SysFile sysFile) {
		sysFileService.update(sysFile);

		return SysResponse.OK();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	// @RequiresPermissions("common:remove")
	public SysResponse remove(Long id, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return SysResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = joinpayConfig.getUploadPath() + sysFileService.get(id).getUrl().replace("/files/", "");
		if (sysFileService.remove(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return SysResponse.error(1, "数据库记录删除成功，文件删除失败");
			}
			return SysResponse.OK();
		} else {
			return SysResponse.error();
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:remove")
	public SysResponse remove(@RequestParam("ids[]") Long[] ids) {
		if ("test".equals(getUsername())) {
			return SysResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		sysFileService.batchRemove(ids);
		return SysResponse.OK();
	}

	@ResponseBody
	@PostMapping("/upload")
	Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return SysResponse.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		String fileName = file.getOriginalFilename();
		fileName = FileUtil.renameToUUID(fileName);
		SysFile sysFile = new SysFile(FileType.fileType(fileName), "/files/" + fileName, new Date());
		try {
			FileUtil.uploadFile(file.getBytes(), joinpayConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			return SysResponse.error();
		}

		if (sysFileService.save(sysFile) > 0) {
			return new HashMap<String, Object>().put("fileName", sysFile.getUrl());
		}
		return SysResponse.error();
	}

}
