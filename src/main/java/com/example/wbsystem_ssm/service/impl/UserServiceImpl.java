package com.example.wbsystem_ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wbsystem_ssm.dao.UserDao;
import com.example.wbsystem_ssm.entity.User;
import com.example.wbsystem_ssm.service.UserService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserDao, User> implements UserService {
//public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
