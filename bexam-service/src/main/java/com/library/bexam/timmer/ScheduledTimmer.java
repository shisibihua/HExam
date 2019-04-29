package com.library.bexam.timmer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器类
 */
@Component
@Configuration
public class ScheduledTimmer {

    Logger logger = LoggerFactory.getLogger(ScheduledTimmer.class);

    //TODO:每天2点执行，时间可根据需要修改
    @Scheduled(cron = "0 0 2 * * ?")
    public void timer() {

    }
}
