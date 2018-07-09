package com.joinpay.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joinpay.entity.SysRole;
import com.joinpay.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Override
	public List<SysRole> list() {
		return null;
	}

	@Override
	public List<SysRole> list(Long userId) {
		return null;
	}

	@Override
	public SysRole get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(SysRole role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SysRole role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int remove(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int batchremove(Long[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

}
