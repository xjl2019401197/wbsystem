package com.example.wbsystem_ssm.controller;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.wbsystem_ssm.executor.MyScheduledTask;
import com.example.wbsystem_ssm.entity.Constant;
import com.example.wbsystem_ssm.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StartController {

    @Autowired
    private MyScheduledTask scheduledTask;

    @GetMapping(value = "/startOrChangeCron")
    public String changeCron() {
        List<TaskEntity> list = null;
        if (CollectionUtils.isEmpty(list)) {
            // 这里模拟存在数据库的数据
            list = Constant.list;
        }
        scheduledTask.refresh(list);
        return "task任务:" + list.toString() + "已经开始运行";
    }

    @GetMapping(value = "/stopCron")
    public String stopCron() {
        List<TaskEntity> list = null;
        if (CollectionUtils.isEmpty(list)) {
            // 这里模拟将要停止的cron可通过前端传来
            list = Constant.list;
        }
        scheduledTask.stop(list);
        List<Integer> collect = list.stream().map(TaskEntity::getTaskId).collect(Collectors.toList());
        return "task任务:" + collect.toString() + "已经停止启动";
    }

    @GetMapping(value = "/restartCorn")
    public String restartCorn() {
        List<TaskEntity> list = null;
        if (CollectionUtils.isEmpty(list)) {
            // 这里模拟存在数据库的数据
            list = Constant.list;
        }
        scheduledTask.stop(list);
        scheduledTask.refresh(list);
        return "task任务:" + list.toString() + "已经重启";
    }
}