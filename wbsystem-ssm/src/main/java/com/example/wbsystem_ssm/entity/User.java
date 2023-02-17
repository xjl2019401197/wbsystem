package com.example.wbsystem_ssm.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 100L;

    /**
     * 用户主键
     */
    @TableId(type = IdType.AUTO)
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户类型，0：普通用户，1：管理员
     */
    private Integer userType;

    /**
     * 身份证
     */
    private String idCard;


    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * qq
     */
    private String qq;

    /**
     * 微信
     */
    private String wxchat;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别：0：女，1：男：2：保密
     */
    private Integer sex;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否逻辑删除 false：否,true： 是	
     */
    private Integer deleteFlag;

    /**
     * 是否禁用 0：否,1： 是
     */
    private Integer state;
}
