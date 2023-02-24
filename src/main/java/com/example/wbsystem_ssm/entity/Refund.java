package com.example.wbsystem_ssm.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
* 退费
* */
public class Refund implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer refundId;

    private Integer userId;

    private Integer cardId;

    private Integer consumerId;

    private Integer createUser;

    private LocalDateTime createTime;

    private Integer updateUser;

    private LocalDateTime updateTime;
     /**
     * 是否逻辑删除 false：否,true： 是
     */
    private String deleteFlag;


}
