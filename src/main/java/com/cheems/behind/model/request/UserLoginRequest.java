package com.cheems.behind.model.request;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class UserLoginRequest {

    private String userAccount;
    private String userPassword;
    private HttpServletRequest request;
}
