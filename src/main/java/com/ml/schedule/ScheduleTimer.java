package com.ml.schedule;

import com.ml.cache.KaptchaCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author panda.
 * @since 2017-09-27 10:23.
 */
@Configuration
@EnableScheduling
public class ScheduleTimer {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTimer.class);
    //注入需要调用的service ,配置定时任务

    @Scheduled(cron = "0 0/5 * * * ?")
    private void executeCleanKaptchaTask() {
        KaptchaCache.getInstance().remove();
    }

}

/**
 * 秒 分钟 小时 天...
 * <example>
 * 每天早八点到晚八点，间隔2分钟执行任务
 *
 * @Scheduled(cron="0 0/2 8-20 * * ?")
 * </example>
 * <example>
 * 每天12点执行任务
 * @Scheduled(cron="0 0 12 * * ?")
 * </example>
 * <p>
 * * <example>
 * 间隔20秒执行
 * @Scheduled(cron="0/20 * * * * ?")
 * </example>
 */