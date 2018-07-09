package com.joinpay.service;

import com.joinpay.entity.SysFile;

public interface FileService {

	boolean isExist(String url);

	SysFile get(Long picId);

	
}
