package com.joinpay.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.joinpay.config.JoinpayConfig;
import com.joinpay.dao.generator.SysFileMapper;
import com.joinpay.entity.SysFile;
import com.joinpay.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private SysFileMapper sysFileMapper;
	@Autowired
	private JoinpayConfig joinpayConfig;

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

	@Override
	public SysFile get(Long id) {
		return sysFileMapper.selectByPrimaryKey(id);
	}

}
