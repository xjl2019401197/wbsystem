package com.example.wbsystem_ssm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TopUp implements Serializable {

    private static final long serialVersionUID = 101541654L;

    /**
     * 卡id
     */
    @TableId(type = IdType.AUTO)
    private Integer topUpId;

    /**
     * 卡id
     */
    private Integer cardId;

    /**
     * 用户id
     */
    private Integer userId;

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
    private Date updateTime;

    /**
     * 是否逻辑删除 false：否,true： 是	
     */
    private String deleteFlag;

}
