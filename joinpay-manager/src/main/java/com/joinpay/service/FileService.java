package com.joinpay.service;

import java.util.List;
import java.util.Map;

import com.joinpay.entity.SysFile;

public interface FileService {

	SysFile get(Long id);

	List<SysFile> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(SysFile sysFile);

	int update(SysFile sysFile);

	int remove(Long id);

	int batchRemove(Long[] ids);

	/**
	 * 判断一个文件是否存在
	 * 
	 * @param url
	 *            SysFile中存的路径
	 * @return
	 */
	Boolean isExist(String url);

}
