package com.example.wbsystem_ssm.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto implements Serializable {

    private static final long serialVersionUID = 101541654L;
    /**
     * 卡id
     */
    @TableId(type = IdType.AUTO)
    @TableField("card_id")
    private Integer cardId;

    /**
     * 卡类型，0：会员卡，1：临时卡
     */
    @TableField("card_type")
    private Integer cardType;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 用户名称
     */
    private String idCard;
    /**
     * 会员等级
     */
    @TableField("level")
    private Integer level;

    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 性别：0：女，1：男：2：保密
     */
    @TableField("sex")
    private Integer sex;


    /**
     * 已消费金额
     */
    @TableField("spend_money")
    private Double spendMoney;

    /**
     * 总充值金额
     */
    @TableField("total_money")
    private Double totalMoney;

    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;




    /**
     * 是否开机 0：否,1： 是
     */
    @TableField("state")
    private Integer state;
}
