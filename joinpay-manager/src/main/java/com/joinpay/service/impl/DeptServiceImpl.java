package com.joinpay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joinpay.common.BuildTree;
import com.joinpay.common.Tree;
import com.joinpay.dao.self.DeptMapper;
import com.joinpay.entity.SysDept;
import com.joinpay.service.DeptService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptMapper sysDeptMapper;

	@Override
	public SysDept get(Long deptId){
		return sysDeptMapper.get(deptId);
	}

	@Override
	public List<SysDept> list(Map<String, Object> map){
		return sysDeptMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysDeptMapper.count(map);
	}

	@Override
	public int save(SysDept sysDept){
		return sysDeptMapper.save(sysDept);
	}

	@Override
	public int update(SysDept sysDept){
		return sysDeptMapper.update(sysDept);
	}

	@Override
	public int remove(Long deptId){
		return sysDeptMapper.remove(deptId);
	}

	@Override
	public int batchRemove(Long[] deptIds){
		return sysDeptMapper.batchRemove(deptIds);
	}

	@Override
	public Tree<SysDept> getTree() {
		List<Tree<SysDept>> trees = new ArrayList<Tree<SysDept>>();
		List<SysDept> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
		for (SysDept sysDept : sysDepts) {
			Tree<SysDept> tree = new Tree<SysDept>();
			tree.setId(sysDept.getDeptId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<SysDept> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public boolean checkDeptHasUser(Long deptId) {
		// TODO Auto-generated method stub
		//查询部门以及此部门的下级部门
		int result = sysDeptMapper.getDeptUserNumber(deptId);
		return result==0?true:false;
	}

}
