package com.joinpay.service;

import java.util.List;

import com.joinpay.entity.SysRole;

public interface RoleService {

	SysRole get(Long id);

	List<SysRole> list();

	int save(SysRole role);

	int update(SysRole role);

	int remove(Long id);

	List<SysRole> list(Long userId);

	int batchremove(Long[] ids);

}
