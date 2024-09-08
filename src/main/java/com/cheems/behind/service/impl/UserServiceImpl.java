package com.cheems.behind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheems.behind.commons.BaseResponseResult;
import com.cheems.behind.commons.UserConstants;
import com.cheems.behind.exception.ReturnCode;
import com.cheems.behind.exception.UserException;
import com.cheems.behind.mapper.UserMapper;
import com.cheems.behind.model.domain.User;
import com.cheems.behind.service.UserService;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @author cheems
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-06 14:19:35
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {


    /**
     * 登录实现
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 返回类
     */
    @Override
    public BaseResponseResult login(String userAccount, String userPassword, HttpServletRequest request) {
        if(StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new UserException(ReturnCode.PARAM_ERROR,"账号与密码不能为空");
        }
        if(userAccount.length() <5) {
            throw new UserException(ReturnCode.PARAM_ERROR,"账号长度必须大于5");
        }
        if(userPassword.length() <8) {
            throw new UserException(ReturnCode.PARAM_ERROR,"密码长度必须大于8");
        }
        String md5Password = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        userQueryWrapper.eq("user_password", md5Password);
        User user = this.getOne(userQueryWrapper);
        if(user == null) {
            throw new UserException(ReturnCode.PARAM_ERROR,"账号与密码不匹配");
        }
        request.getSession().setAttribute(UserConstants.USER_LOGIN_INFO, user);
        return  new BaseResponseResult(ReturnCode.SUCCESS,"登录成功");

    }

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    @Override
    public BaseResponseResult<User> currentUser(HttpServletRequest request) {
        User user =(User) request.getSession().getAttribute(UserConstants.USER_LOGIN_INFO);
        if(user == null) {
            throw new UserException(ReturnCode.NOT_LOGIN,"请先登录");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", user.getUserAccount());
        User currentUser = this.getOne(userQueryWrapper);
        User safeUser = getSafeUser(currentUser);
        return new BaseResponseResult(ReturnCode.SUCCESS,safeUser);
    }

    @Override
    public BaseResponseResult register(String userAccount, String userPassword, String checkPassword) {
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new UserException(ReturnCode.PARAM_ERROR,"账号、密码与确认密码不能为空");
        }
        if(userAccount.length() <5) {
            throw new UserException(ReturnCode.PARAM_ERROR,"账号长度必须大于5");
        }
        if(userPassword.length() <8) {
            throw new UserException(ReturnCode.PARAM_ERROR,"密码长度必须大于8");
        }
        if(checkPassword.length() <8) {
            throw new UserException(ReturnCode.PARAM_ERROR,"确认密码长度必须大于8");
        }
        String regEx = "\\pP|\\pS|\\s+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(userAccount);
        if(matcher.find()) {
            throw new UserException(ReturnCode.PARAM_ERROR,"账号不能包含特殊字符");
        }
        if(!checkPassword.equals(userPassword)) {
            throw new UserException(ReturnCode.PARAM_ERROR,"两次输入的密码不一致");
        }

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        long count = this.count(userQueryWrapper);
        if(count > 0) {
            throw new UserException(ReturnCode.PARAM_ERROR,"账号重复");
        }

        String md5Password = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(md5Password);
        boolean save = this.save(user);
        if(!save) {
            throw new UserException(ReturnCode.SYSTEM_ERROR,"注册失败,请联系管理员");
        }
        return new BaseResponseResult(ReturnCode.SUCCESS);
    }

    @Override
    public BaseResponseResult logout(HttpServletRequest request) {
        if(request.getSession().getAttribute(UserConstants.USER_LOGIN_INFO)==null){
            throw  new UserException(ReturnCode.NOT_LOGIN,"请先登录");
        }

        request.getSession().removeAttribute(UserConstants.USER_LOGIN_INFO);
        return new BaseResponseResult(ReturnCode.SUCCESS);


    }

    //todo 优化搜索
    @Override
    public BaseResponseResult<List<User>> search(HttpServletRequest request,String username) {
            if(!isAdmin(request)){
                throw new UserException(ReturnCode.NO_AUTH,"无权限");
            }
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            if(StringUtils.isNotBlank(username)) {
                userQueryWrapper.like("username", username);
            }
        List<User> userlist = this.list(userQueryWrapper);
        userlist= userlist.stream().map(this::getSafeUser).collect(Collectors.toList());
        return new BaseResponseResult<>(ReturnCode.SUCCESS,userlist);
    }

    public User getSafeUser(User olduser) {
        User safetyUser= new User();
        BeanUtils.copyProperties(olduser, safetyUser);
        safetyUser.setUserPassword("");
        return safetyUser;
    }
    public boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstants.USER_LOGIN_INFO);
        return user != null && user.getUserRole() == 1;
    }


}




