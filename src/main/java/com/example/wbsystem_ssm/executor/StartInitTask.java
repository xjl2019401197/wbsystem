package com.example.wbsystem_ssm.executor;

import com.example.wbsystem_ssm.entity.Constant;
import com.example.wbsystem_ssm.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartInitTask implements CommandLineRunner {

  @Autowired
  private MyScheduledTask myScheduledTask;

  @Override
  public void run(String... args) throws Exception {
    List<TaskEntity> list = Constant.list;
    System.out.println(list);
    myScheduledTask.configureTasks(new ScheduledTaskRegistrar());
    myScheduledTask.refresh(list);
  }
}