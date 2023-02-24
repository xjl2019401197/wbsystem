package com.example.wbsystem_ssm.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * 设备
 * */
public class Equip  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    @TableId(type = IdType.AUTO)
    private Integer equipId;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 剩余机型数量
     */
    private Integer reduce;
    /**
     * 会员价格
     */
    private Integer vipPrice;
    /**
     * 非会员价格
     */
    private Integer noVipPrice;

}
