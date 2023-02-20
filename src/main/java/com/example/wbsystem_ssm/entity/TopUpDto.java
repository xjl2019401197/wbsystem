package com.example.wbsystem_ssm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * 充值
 * */
public class TopUpDto implements Serializable {

    private static final long serialVersionUID = 101541654L;

    /**
     * 卡id
     */
    private Integer topUpId;


    /**
     * 用户身份证
     */
    private String idCard;

    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 充值金额
     */
    private Double money;

    /**
     *
     */
    private String way;



    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;


}
