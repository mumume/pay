package com.joinpay.common;

import org.apache.commons.lang.StringUtils;

import com.joinpay.enums.Code;

/**
 * http请求返回的标准实体类
 * 
 * @ClassName: HttpResponse
 * @Description: TODO
 * @author lil
 * @date 2018年1月18日 下午3:48:36
 *
 */
public class SysResponse {
	/**
	 * 请求返回的状态码
	 */
	private int code;
	/**
	 * 请求返回的消息
	 */
	private String message;
	/**
	 * 请求返回的数据
	 */
	private Object data;

	public SysResponse() {

	}

	public static SysResponse error(int code, String message) {
		return new SysResponse(message, code, null);
	}

	public static SysResponse error() {
		return new SysResponse(Code.SystemError.getMessage(), Code.SystemError.getCode(), null);
	}

	public static SysResponse OK(Object object) {
		return new SysResponse(Code.OK.getMessage(), Code.OK.getCode(), object);
	}

	public static SysResponse OK() {
		return new SysResponse(Code.OK.getMessage(), Code.OK.getCode(), null);
	}

	public static SysResponse create(Code code) {
		return new SysResponse(code.getMessage(), code.getCode(), null);
	}

	public static SysResponse create(Code code, String message) {
		String msg = code.getMessage();
		if (StringUtils.isNotBlank(message)) {
			msg += "：" + message;
		}
		return new SysResponse(msg, code.getCode(), null);
	}

	public static SysResponse create(Code code, String message, Object object) {
		String msg = code.getMessage();
		if (StringUtils.isNotBlank(message)) {
			msg += "：" + message;
		}
		return new SysResponse(msg, code.getCode(), object);
	}

	public static SysResponse create(Code code, Object object) {
		return new SysResponse(code.getMessage(), code.getCode(), object);
	}

	public SysResponse(String message, int code, Object data) {
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
