package com.joinpay.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joinpay.entity.BlogContent;
import com.joinpay.service.ContentService;
import com.joinpay.util.DateUtils;
import com.joinpay.util.PageUtils;

@RequestMapping("/blog")
@Controller
public class BlogController {
	@Autowired
	ContentService bContentService;

	@GetMapping()
	String blog() {
		return "blog/index/main";
	}

	@ResponseBody
	@GetMapping("/open/list")
	public PageUtils opentList(@RequestParam Map<String, Object> params) {
		// Query query = new Query(params);
		// List<BlogContent> bContentList = bContentService.list(query);
		// int total = bContentService.count(query);
		// PageUtils pageUtils = new PageUtils(bContentList, total);
		return null;
	}

	@GetMapping("/open/post/{cid}")
	String post(@PathVariable("cid") Long cid, Model model) {
		BlogContent bBlogContent = bContentService.get(cid);
		model.addAttribute("bContent", bBlogContent);
		model.addAttribute("gtmModified", DateUtils.format(bBlogContent.getGtmModified()));
		return "blog/index/post";
	}

	@GetMapping("/open/page/{categories}")
	String about(@PathVariable("categories") String categories, Model model) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("categories", categories);
		BlogContent bBlogContent = null;
		if (bContentService.list(map).size() > 0) {
			bBlogContent = bContentService.list(map).get(0);
		}
		model.addAttribute("bContent", bBlogContent);
		return "blog/index/post";
	}
}
