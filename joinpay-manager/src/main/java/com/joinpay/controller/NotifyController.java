package com.joinpay.controller;

import java.util.Date;
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
import com.joinpay.entity.DictDO;
import com.joinpay.entity.NotifyDO;
import com.joinpay.entity.NotifyRecordDO;
import com.joinpay.enums.Code;
import com.joinpay.service.DictService;
import com.joinpay.service.NotifyRecordService;
import com.joinpay.service.NotifyService;
import com.joinpay.util.PageUtils;
import com.joinpay.util.Query;

/**
 * 通知通告
 *
 */

@Controller
@RequestMapping("/oa/notify")
public class NotifyController extends BaseController {
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private NotifyRecordService notifyRecordService;
	@Autowired
	private DictService dictService;

	@GetMapping()
	@RequiresPermissions("oa:notify:notify")
	String oaNotify() {
		return "oa/notify/notify";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("oa:notify:notify")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<NotifyDO> notifyList = notifyService.list(query);
		int total = notifyService.count(query);
		PageUtils pageUtils = new PageUtils(notifyList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("oa:notify:add")
	String add() {
		return "oa/notify/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("oa:notify:edit")
	String edit(@PathVariable("id") Long id, Model model) {
		NotifyDO notify = notifyService.get(id);
		List<DictDO> dictDOS = dictService.listByType("oa_notify_type");
		String type = notify.getType();
		for (DictDO dictDO : dictDOS) {
			if (type.equals(dictDO.getValue())) {
				dictDO.setRemarks("checked");
			}
		}
		model.addAttribute("oaNotifyTypes", dictDOS);
		model.addAttribute("notify", notify);
		return "oa/notify/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("oa:notify:add")
	public SysResponse save(NotifyDO notify) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		notify.setCreateBy(getUserId());
		if (notifyService.save(notify) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("oa:notify:edit")
	public SysResponse update(NotifyDO notify) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		notifyService.update(notify);
		return SysResponse.OK();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("oa:notify:remove")
	public SysResponse remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		if (notifyService.remove(id) > 0) {
			return SysResponse.OK();
		}
		return SysResponse.create(Code.SystemError);
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("oa:notify:batchRemove")
	public SysResponse remove(@RequestParam("ids[]") Long[] ids) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return SysResponse.create(Code.SystemError, "演示系统不允许修改,完整体验请部署程序");
		}
		notifyService.batchRemove(ids);
		return SysResponse.OK();
	}

	@ResponseBody
	@GetMapping("/message")
	PageUtils message() {
		Map<String, Object> params = new HashMap<>(16);
		params.put("offset", 0);
		params.put("limit", 3);
		Query query = new Query(params);
		query.put("userId", getUserId());
		query.put("isRead", Constant.OA_NOTIFY_READ_NO);
		return notifyService.selfList(query);
	}

	@GetMapping("/selfNotify")
	String selefNotify() {
		return "oa/notify/selfNotify";
	}

	@ResponseBody
	@GetMapping("/selfList")
	PageUtils selfList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		query.put("userId", getUserId());

		return notifyService.selfList(query);
	}

	@GetMapping("/read/{id}")
	@RequiresPermissions("oa:notify:edit")
	String read(@PathVariable("id") Long id, Model model) {
		NotifyDO notify = notifyService.get(id);
		// 更改阅读状态
		NotifyRecordDO notifyRecordDO = new NotifyRecordDO();
		notifyRecordDO.setNotifyId(id);
		notifyRecordDO.setUserId(getUserId());
		notifyRecordDO.setReadDate(new Date());
		notifyRecordDO.setIsRead(Constant.OA_NOTIFY_READ_YES);
		notifyRecordService.changeRead(notifyRecordDO);
		model.addAttribute("notify", notify);
		return "oa/notify/read";
	}

}
