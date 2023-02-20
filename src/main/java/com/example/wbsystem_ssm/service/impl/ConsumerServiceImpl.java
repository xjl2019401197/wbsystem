package com.example.wbsystem_ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wbsystem_ssm.dao.ConsumerDao;
import com.example.wbsystem_ssm.entity.Consumer;
import com.example.wbsystem_ssm.service.ConsumerService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl extends ServiceImpl<ConsumerDao, Consumer> implements ConsumerService {

}
