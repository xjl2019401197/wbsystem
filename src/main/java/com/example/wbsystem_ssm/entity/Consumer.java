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
/*
* 消费
* */
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消费id
     */
    /**
     * 卡id
     */
    @TableId(type = IdType.AUTO)
    private Integer consumeId;

    /**
     * 用户id
     */
    private Integer  userId;

    /**
     * 卡id
     */
    private Integer cardId;

    /**
     * 上机时间
     */
    private LocalDateTime startTime;

    /**
     * 下机时间
     */
    private LocalDateTime endTime;

    /**
     * 消费金额
     */
    private Double consumeMoney;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

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
    private String deleteFlag;

}
