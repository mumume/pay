package com.joinpay.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joinpay.common.Tree;
import com.joinpay.dao.generator.SysUserMapper;
import com.joinpay.dao.self.UserMapper;
import com.joinpay.entity.SysDept;
import com.joinpay.entity.SysUser;
import com.joinpay.service.UserService;
import com.joinpay.util.Query;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	SysUserMapper sysUserMapper;
	@Autowired
	UserMapper userMapper;

	@Override
	public SysUser get(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<SysUser> list(Query query) {
		return userMapper.list(query);
	}

	@Override
	public int count(Query query) {
		return 0;
	}

	@Override
	public int save(SysUser user) {
		return 0;
	}

	@Override
	public int update(SysUser user) {
		return 0;
	}

	@Override
	public int updatePersonal(SysUser user) {
		return 0;
	}

	@Override
	public int remove(Long id) {
		return 0;
	}

	@Override
	public int batchremove(Long[] userIds) {
		return 0;
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		return false;
	}

	@Override
	public void resetPwd(SysUser userVO, SysUser user) {

	}

	@Override
	public void adminResetPwd(SysUser userVO) {

	}

	@Override
	public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) {
		return null;
	}

	@Override
	public Tree<SysDept> getTree() {
		return null;
	}

}
