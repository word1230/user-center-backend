package com.cheems.behind.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class UserRegisterRequest {

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
