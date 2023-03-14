package com.example.wbsystem_ssm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wbsystem_ssm.entity.Card;
import com.example.wbsystem_ssm.entity.TopUp;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TopUpDao   extends MPJBaseMapper<TopUp> {

}
