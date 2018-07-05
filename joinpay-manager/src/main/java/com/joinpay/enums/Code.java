package com.joinpay.enums;

/**
 * 返回状态码枚举
 * 
 * @ClassName: Code
 * @Description: TODO
 * @author lil
 * @date 2018年1月18日 下午3:36:01
 *
 */
public enum Code {

    OK(200, "操作成功"),
    
    NOTEXIST(700, "数据不存在"),
    EXIST(800, "数据已存在"),
    
    SystemError(10001, "系统异常"),
    ParamError(10002, "参数异常"),
    
    NotLogin(10030, "未登陆"),
    PassWrong(10031,"密码错误"),
    Overtime(10032,"登录超时"),
    
    SizeOver(10050,"文件过大！"),
    ReadFileError(10051, "文件读取异常"),
    UploadError(10052, "文件上传异常"),
    DelFileError(10053, "文件删除失败"),
    
    NoActionAuthority(10040,"无操作权限"),
    NoProcessAuthority(10041,"无流程权限"),
    NoProcess(10042,"流程错误"),
    AuthorityError(10043,"权限异常"),
	;
	
    private int code;
    private String message;


    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
