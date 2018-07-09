package com.joinpay.dao.self;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.joinpay.entity.SysMenu;

/**
 * 菜单管理
 * 
 */
@Mapper
public interface MenuMapper {

	SysMenu get(Long menuId);

	List<SysMenu> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(SysMenu menu);

	int update(SysMenu menu);

	int remove(Long menuId);

	int batchRemove(Long[] menuIds);

	List<SysMenu> listMenuByUserId(Long id);

	List<String> listUserPerms(Long id);
}