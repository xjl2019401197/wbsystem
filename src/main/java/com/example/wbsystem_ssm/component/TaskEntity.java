package com.example.wbsystem_ssm.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
  /**
   * 任务id
   */
  private int taskId;
  /**
   * 任务说明
   */
  private String desc;
  /**
   * cron 表达式
   */
  private String expression;
}