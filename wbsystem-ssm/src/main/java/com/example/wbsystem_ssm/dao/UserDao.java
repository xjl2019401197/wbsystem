package com.example.wbsystem_ssm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wbsystem_ssm.entity.User;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao  extends MPJBaseMapper<User> {
//public interface UserDao  extends  BaseMapper<User> {

}
