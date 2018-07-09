package com.joinpay.dao.generator;

import com.joinpay.entity.BlogContent;

public interface BlogContentMapper {
    int deleteByPrimaryKey(Long cid);

    int insert(BlogContent record);

    int insertSelective(BlogContent record);

    BlogContent selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(BlogContent record);

    int updateByPrimaryKeyWithBLOBs(BlogContent record);

    int updateByPrimaryKey(BlogContent record);
}