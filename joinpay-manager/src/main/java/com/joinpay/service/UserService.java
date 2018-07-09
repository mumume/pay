package com.joinpay.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.joinpay.common.Tree;
import com.joinpay.entity.SysDept;
import com.joinpay.entity.SysUser;
import com.joinpay.util.Query;

public interface UserService {

	SysUser get(Long id);

	List<SysUser> list(Query query);

	int count(Query query);

	int save(SysUser user);

	int update(SysUser user);

	int updatePersonal(SysUser user);

	int remove(Long id);

	int batchremove(Long[] userIds);

	boolean exit(Map<String, Object> params);

	void resetPwd(SysUser userVO, SysUser user);

	void adminResetPwd(SysUser userVO);

	Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId);

	Tree<SysDept> getTree();

}
