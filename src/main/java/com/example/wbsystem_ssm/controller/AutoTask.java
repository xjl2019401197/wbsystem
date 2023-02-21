package com.example.wbsystem_ssm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Slf4j
public class AutoTask {
    @Scheduled(cron="*/6 * * * * ?")
    private void process()
    {
        log.info("autoTask ");
    } }