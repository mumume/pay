package com.joinpay.common;

import com.joinpay.enums.Code;

/**
 * 业务异常信息类
 * 
 * @ClassName: BizException
 * @Description: TODO
 * @author lil
 * @date 2018年1月18日 下午3:47:06
 *
 */
public final class BizException extends RuntimeException {

	private static final long serialVersionUID = 8342451586322744222L;
	/**
	 * 业务异常码
	 */
	private Code codes;
	/**
	 * 异常信息
	 */
	private String bizMsg;

	/**
	 * 私有构造函数
	 *
	 * @param codes
	 * @param bizMessage
	 */
	private BizException(Code codes, String bizMessage) {

		super(codes.getMessage() + "：" + bizMessage);
		this.codes = codes;
		this.bizMsg = bizMessage;
	}

	/**
	 * 静态工厂方法
	 *
	 * @return
	 */
	public static BizException create(Code code) {
		return new BizException(code, null);
	}

	public static BizException create(Code code, String msg) {
		return new BizException(code, msg);
	}

	public Code getCodes() {
		return codes;
	}

	public void setCodes(Code codes) {
		this.codes = codes;
	}

	public String getBizMsg() {
		return bizMsg;
	}

	public void setBizMsg(String bizMsg) {
		this.bizMsg = bizMsg;
	}
}
