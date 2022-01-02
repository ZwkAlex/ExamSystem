package com.group.exam.schedule;

import com.group.exam.dao.ExamTimerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ExamCheckSchedule {
    private static final Logger log = LoggerFactory.getLogger(ExamCheckSchedule.class);
    @Resource
    ExamTimerMapper examTimerMapper;

    @Scheduled(cron = "*/6 * * * * ?")
    private void process() {
        log.info(String.format("删除 %d 条过期信息。", examTimerMapper.deleteExpired()));
    }
}
