package com.cheems.behind.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 账号是否正常 0正常 1被禁用
     */
    private Integer status;

    /**
     * 用户角色 0普通用户 1管理员
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否被删除 默认0 ，被删除1
     */
    @TableLogic
    private Integer deleted;

    /**
     * 用户头像
     */
    private String avatarUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}