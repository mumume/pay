package com.joinpay.dao.self;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.joinpay.entity.SysUser;

@Mapper
public interface UserMapper {

	List<SysUser> list();
}
