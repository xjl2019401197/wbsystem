package com.example.wbsystem_ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wbsystem_ssm.dao.TopUpDao;
import com.example.wbsystem_ssm.entity.TopUp;
import com.example.wbsystem_ssm.service.TopUpService;
import org.springframework.stereotype.Service;

@Service
public class TopUpServiceImpl extends ServiceImpl<TopUpDao, TopUp> implements TopUpService {

}
