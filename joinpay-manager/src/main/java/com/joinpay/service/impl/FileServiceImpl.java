package com.joinpay.service.impl;

import org.springframework.stereotype.Service;

import com.joinpay.entity.SysFile;
import com.joinpay.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public boolean isExist(String url) {
		return false;
	}

	@Override
	public SysFile get(Long picId) {
		return null;
	}

}
