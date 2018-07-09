package com.joinpay.dao.self;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.joinpay.entity.SysFile;

/**
 * 文件上传
 */
@Mapper
public interface FileMapper {

	SysFile get(Long id);
	
	List<SysFile> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SysFile file);
	
	int update(SysFile file);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
