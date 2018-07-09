package com.joinpay.dao.self;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.joinpay.entity.SysRoleMenu;

/**
 * 角色与菜单对应关系
 */
@Mapper
public interface RoleMenuDao {

	SysRoleMenu get(Long id);

	List<SysRoleMenu> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(SysRoleMenu roleMenu);

	int update(SysRoleMenu roleMenu);

	int remove(Long id);

	int batchRemove(Long[] ids);

	List<Long> listMenuIdByRoleId(Long roleId);

	int removeByRoleId(Long roleId);

	int removeByMenuId(Long menuId);

	int batchSave(List<SysRoleMenu> list);
}
