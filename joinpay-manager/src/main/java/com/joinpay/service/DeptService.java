package com.joinpay.service;

import java.util.List;
import java.util.Map;

import com.joinpay.common.Tree;
import com.joinpay.entity.SysDept;

public interface DeptService {

	List<SysDept> list(Map<String, Object> query);

	SysDept get(Long pId);

	int count(Map<String, Object> map);

	boolean checkDeptHasUser(Long deptId);

	int remove(Long deptId);

	int batchRemove(Long[] deptIds);

	Tree<SysDept> getTree();

	int save(SysDept sysDept);

	int update(SysDept sysDept);

}
