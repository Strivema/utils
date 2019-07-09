package com.ray.utils;

import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ray.Ma
 * @date 2019/7/9 10:34
 */
public class VerifyScheduleUtils {
    public static void verify() throws ParseException {
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setCronExpression("0 40 */1 * * ?");
        trigger.triggered(null);

        Date time = trigger.getNextFireTime();

        for (int i = 0; i < 4; i++) {
            if (time != null) {
                System.out.println("next fire time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time));
                time = trigger.getFireTimeAfter(time);
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        verify();
    }
}
