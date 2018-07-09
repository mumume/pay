package com.joinpay.dao.self;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.joinpay.entity.SysUserRole;

@Mapper
public interface UserRoleMapper {
	SysUserRole get(Long id);

	List<SysUserRole> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(SysUserRole userRole);

	int update(SysUserRole userRole);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<Long> listRoleId(Long userId);

	int removeByUserId(Long userId);

	int removeByRoleId(Long roleId);

	int batchSave(List<SysUserRole> list);

	int batchRemoveByUserId(Long[] ids);
}
