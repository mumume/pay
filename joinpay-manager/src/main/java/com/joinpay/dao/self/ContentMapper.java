package com.joinpay.dao.self;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.joinpay.entity.BlogContent;

/**
 * 文章内容
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 16:17:48
 */
@Mapper
public interface ContentMapper {

	BlogContent get(Long cid);

	List<BlogContent> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(BlogContent content);

	int update(BlogContent content);

	int remove(Long cid);

	int batchRemove(Long[] cids);
}
