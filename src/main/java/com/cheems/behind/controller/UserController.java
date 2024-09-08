package com.cheems.behind.controller;

import com.cheems.behind.commons.BaseResponseResult;
import com.cheems.behind.exception.ReturnCode;
import com.cheems.behind.exception.UserException;
import com.cheems.behind.model.domain.User;
import com.cheems.behind.model.request.UserLoginRequest;
import com.cheems.behind.model.request.UserRegisterRequest;
import com.cheems.behind.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * user接口类
 */

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录接口
     *
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponseResult login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(request== null){
            throw new UserException(ReturnCode.SYSTEM_ERROR);
        }
        if(StringUtils.isAnyBlank(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword())) {
            throw new UserException(ReturnCode.PARAM_ERROR,"用户名与密码不能为空");
        }
        return userService.login(userLoginRequest.getUserAccount(), userLoginRequest.getUserPassword(), request);
    }

    @GetMapping("/currentUser")
    public BaseResponseResult<User> getCurrentUser(HttpServletRequest request) {
        return userService.currentUser(request);
    }

    @PostMapping("/register")
    public BaseResponseResult register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPassword(), userRegisterRequest.getCheckPassword());
    }

    @PostMapping("/logout")
    public BaseResponseResult logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    @GetMapping("/search")
    public BaseResponseResult<List<User>> search(HttpServletRequest request,@RequestParam(defaultValue = "") String username) {
        return userService.search(request, username);
    }

}
