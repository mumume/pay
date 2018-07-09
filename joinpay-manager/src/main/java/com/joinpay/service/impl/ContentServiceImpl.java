package com.joinpay.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joinpay.dao.self.ContentMapper;
import com.joinpay.entity.BlogContent;
import com.joinpay.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private ContentMapper bContentMapper;

	@Override
	public BlogContent get(Long cid) {
		return bContentMapper.get(cid);
	}

	@Override
	public List<BlogContent> list(Map<String, Object> map) {
		return bContentMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return bContentMapper.count(map);
	}

	@Override
	public int save(BlogContent bContent) {
		return bContentMapper.save(bContent);
	}

	@Override
	public int update(BlogContent bContent) {
		return bContentMapper.update(bContent);
	}

	@Override
	public int remove(Long cid) {
		return bContentMapper.remove(cid);
	}

	@Override
	public int batchRemove(Long[] cids) {
		return bContentMapper.batchRemove(cids);
	}

}
