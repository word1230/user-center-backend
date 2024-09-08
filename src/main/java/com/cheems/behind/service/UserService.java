package com.cheems.behind.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cheems.behind.commons.BaseResponseResult;
import com.cheems.behind.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author cheems
* @description 针对表【user】的数据库操作Service
* @createDate 2024-09-06 14:19:35
*/
public interface UserService extends IService<User> {


    /**
     * 登录实现
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 返回类
     */
    public BaseResponseResult login(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    public BaseResponseResult<User> currentUser(HttpServletRequest request);

    /**
     * 注册功能实现
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    public BaseResponseResult register(String userAccount, String userPassword,String checkPassword);

    /**
     * 登出接口
     * @param request
     * @return
     */
    public BaseResponseResult logout(HttpServletRequest request);

    public BaseResponseResult<List<User>> search(HttpServletRequest request, String username);
}
