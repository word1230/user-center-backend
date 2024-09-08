package com.cheems.behind.exception;


public enum ReturnCode {

    SUCCESS(20000,"OK"),
    PARAM_ERROR(40000,"参数出错",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTH(40200,"无权限",""),
    SYSTEM_ERROR(50000,"系统错误")



    ;


    private int code;
    private String message;
    private String description;
    ReturnCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
    ReturnCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
