package com.joinpay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joinpay.dao.self.RoleMapper;
import com.joinpay.dao.self.RoleMenuMapper;
import com.joinpay.dao.self.UserMapper;
import com.joinpay.dao.self.UserRoleMapper;
import com.joinpay.entity.SysRole;
import com.joinpay.entity.SysRoleMenu;
import com.joinpay.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {


    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleMenuMapper roleMenuMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public List<SysRole> list() {
        List<SysRole> roles = roleMapper.list(new HashMap<>(16));
        return roles;
    }


    @Override
    public List<SysRole> list(Long userId) {
        List<Long> rolesIds = userRoleMapper.listRoleId(userId);
        List<SysRole> roles = roleMapper.list(new HashMap<>(16));
        for (SysRole roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getRoleId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }
    @Transactional
    @Override
    public int save(SysRole role) {
        int count = roleMapper.save(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        List<SysRoleMenu> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu rmDo = new SysRoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return count;
    }

    @Transactional
    @Override
    public int remove(Long id) {
        int count = roleMapper.remove(id);
        userRoleMapper.removeByRoleId(id);
        roleMenuMapper.removeByRoleId(id);
        return count;
    }

    @Override
    public SysRole get(Long id) {
        SysRole roleDO = roleMapper.get(id);
        return roleDO;
    }

    @Override
    public int update(SysRole role) {
        int r = roleMapper.update(role);
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        roleMenuMapper.removeByRoleId(roleId);
        List<SysRoleMenu> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu rmDo = new SysRoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return r;
    }

    @Override
    public int batchremove(Long[] ids) {
        int r = roleMapper.batchRemove(ids);
        return r;
    }
}
