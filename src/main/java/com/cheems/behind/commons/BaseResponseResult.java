package com.cheems.behind.commons;

import com.cheems.behind.exception.ReturnCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 基础的返回类
 * @param <T>
 * @author cheems
 */

@NoArgsConstructor
@Data
public class BaseResponseResult<T> implements Serializable {

    /**
     * 定义
     */

    private static final long serialVersionUID = 1L;

   private int code;
   private String msg;
   private T data;
   private String description;


   public BaseResponseResult(int code, String msg, String description,T data) {
       this.code = code;
       this.msg = msg;
       this.data = data;
       this.description = description;
   }
    public BaseResponseResult(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }


    public BaseResponseResult(ReturnCode returnCode,T data) {
        this(returnCode.getCode(),returnCode.getMessage(),returnCode.getDescription(),data);
    }
    public BaseResponseResult(ReturnCode returnCode) {
        this(returnCode.getCode(),returnCode.getMessage(),returnCode.getDescription(),null);
    }



}
