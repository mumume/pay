package com.joinpay.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.joinpay.common.Tree;
import com.joinpay.entity.SysDept;
import com.joinpay.entity.SysUser;
import com.joinpay.vo.UserVO;

public interface UserService {

	SysUser get(Long id);

	List<SysUser> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(SysUser user);

	int update(SysUser user);

	int remove(Long userId);

	int batchremove(Long[] userIds);

	boolean exit(Map<String, Object> params);

	Set<String> listRoles(Long userId);

	int resetPwd(UserVO userVO,SysUser userDO) throws Exception;
	int adminResetPwd(UserVO userVO) throws Exception;
	Tree<SysDept> getTree();

	/**
	 * 更新个人信息
	 * @param userDO
	 * @return
	 */
	int updatePersonal(SysUser userDO);

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;

}
