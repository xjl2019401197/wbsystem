package com.example.wbsystem_ssm.component;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TaskServiceJob1Impl implements TaskService {
  @Override
  public void HandlerJob() {
    System.out.println("------job1 开始执行---------："+new Date());

    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  " + Thread.currentThread().getName() + "  任务一启动");
    try {
      Thread.sleep(10000);//任务耗时10秒
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  " + Thread.currentThread().getName() + "  结束");

  }

  @Override
  public Integer jobId() {
    return 1;
  }
}