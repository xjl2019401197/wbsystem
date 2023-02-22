package com.example.wbsystem_ssm.component;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StartController {

  @Autowired
  private MyScheduledTask scheduledTask;

  @PostMapping(value = "/startOrChangeCron")
  public String changeCron(@RequestBody List<TaskEntity> list){
    if (CollectionUtils.isEmpty(list)) {
      // 这里模拟存在数据库的数据
      list = Arrays.asList(
          new TaskEntity(1, "测试1","0/1 * * * * ?") ,
          new TaskEntity(2, "测试2","0/1 * * * * ?")
      );
    }
    scheduledTask.refresh(list);
    return "task任务:" + list.toString() + "已经开始运行";
  }

  @PostMapping(value = "/stopCron")
  public String stopCron(@RequestBody List<TaskEntity> list){
    if (CollectionUtils.isEmpty(list)) {
      // 这里模拟将要停止的cron可通过前端传来
      list = Arrays.asList(
          new TaskEntity(1, "测试1","0/1 * * * * ?") ,
          new TaskEntity(2, "测试2","0/1 * * * * ?")
      );
    }
    scheduledTask.stop(list);
    List<Integer> collect = list.stream().map(TaskEntity::getTaskId).collect(Collectors.toList());
    return "task任务:" + collect.toString() + "已经停止启动";
  }

}