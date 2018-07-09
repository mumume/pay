package com.joinpay.dao.self;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.joinpay.entity.SysUser;

@Mapper
public interface UserMapper {

	List<SysUser> list();

	List<SysUser> list(Map<String, Object> map);
}

