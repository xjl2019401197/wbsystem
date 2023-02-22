package com.example.wbsystem_ssm.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StartInitTask implements CommandLineRunner {

  @Autowired
  private MyScheduledTask myScheduledTask;

  @Override
  public void run(String... args) throws Exception {
    List<TaskEntity> list = Arrays.asList(
        new TaskEntity(1, "测试1", "0/1 * * * * ?"),
        new TaskEntity(2, "测试2", "0/1 * * * * ?")
    );
    System.out.println(list);
    myScheduledTask.refresh(list);
  }
}