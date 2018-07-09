package com.joinpay.dao.self;

import java.util.List;
import java.util.Map;

import com.joinpay.entity.SysRole;

public interface RoleMapper {

	SysRole get(Long roleId);
	
	List<SysRole> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SysRole role);
	
	int update(SysRole role);
	
	int remove(Long roleId);
	
	int batchRemove(Long[] roleIds);
}
