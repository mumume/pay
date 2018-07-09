package com.joinpay.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.joinpay.config.JoinpayConfig;
import com.joinpay.dao.self.FileMapper;
import com.joinpay.entity.SysFile;
import com.joinpay.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileMapper sysFileMapper;
	@Autowired
	private JoinpayConfig joinpayConfig;

	@Override
	public SysFile get(Long id) {
		return sysFileMapper.get(id);
	}

	@Override
	public List<SysFile> list(Map<String, Object> map) {
		return sysFileMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return sysFileMapper.count(map);
	}

	@Override
	public int save(SysFile sysFile) {
		return sysFileMapper.save(sysFile);
	}

	@Override
	public int update(SysFile sysFile) {
		return sysFileMapper.update(sysFile);
	}

	@Override
	public int remove(Long id) {
		return sysFileMapper.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return sysFileMapper.batchRemove(ids);
	}

	@Override
	public Boolean isExist(String url) {
		Boolean isExist = false;
		if (!StringUtils.isEmpty(url)) {
			String filePath = url.replace("/files/", "");
			filePath = joinpayConfig.getUploadPath() + filePath;
			File file = new File(filePath);
			if (file.exists()) {
				isExist = true;
			}
		}
		return isExist;
	}

}
