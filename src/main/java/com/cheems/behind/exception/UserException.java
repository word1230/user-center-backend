package com.cheems.behind.exception;


import lombok.Data;

@Data
public class UserException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private int code;
    private  String message;
    private  String description;

    public UserException(ReturnCode returnCode, String description) {
        this.code = returnCode.getCode();
        this.message = returnCode.getMessage();
        this.description = description;
    }
    public UserException(ReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.message = returnCode.getMessage();
        this.description = returnCode.getDescription();
    }

}
