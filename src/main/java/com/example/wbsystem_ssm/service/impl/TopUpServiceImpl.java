package com.example.wbsystem_ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wbsystem_ssm.dao.TopUpDao;
import com.example.wbsystem_ssm.entity.TopUp;
import com.example.wbsystem_ssm.service.TopUpService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TopUpServiceImpl extends MPJBaseServiceImpl<TopUpDao, TopUp> implements TopUpService {

}
