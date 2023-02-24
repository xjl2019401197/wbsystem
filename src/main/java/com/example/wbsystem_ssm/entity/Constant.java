package com.example.wbsystem_ssm.entity;

import java.util.Arrays;
import java.util.List;

public class Constant {
    public static final List<TaskEntity> list =  Arrays.asList(
            new TaskEntity(1, "测试1", "0/60 * * * * ?")
    );
}
