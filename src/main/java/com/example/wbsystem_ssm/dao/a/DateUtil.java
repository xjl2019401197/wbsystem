package com.example.wbsystem_ssm.dao.a;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 * @create 2020-02-21 15:44
 */
public class DateUtil {

    /**
     * 获取昨天的日期
     * @return
     */
    public static String getYesterday(){
        Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMd-d");

        return simpleDateFormat.format(today);
    }

    /**
     * 获取今天的日期
     * @return
     */
    public static String getNowday(){
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(today);
        return format;
    }

    /**
     *  //获取明天的日期
     * @return
     */
    public static String getTomorrow(){
        Date today2 = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(today2);
    }

    /**
     * 获取今天的日期
     * @return
     */
    public static String getNowMonth(){
        Date today = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String format = simpleDateFormat.format(today);
        return format;
    }

    /**
     *  //获取明天的日期
     * @return
     */
    public static String getNextMonth(){
        Date today2 = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 30);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(today2);
    }


    /**
     * 格式化日期字符串："10/10/2013 11:30:10"
     * @param date
     * @return
     */
    public static Date simpleDateFormat(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return date1;
    }

    //计算开始时间与结束时间之间相差多少小时
    public static Long getDatePoor(Date endDate, Date startDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少小时
        long hour = diff % nd / nh;
        return hour;
    }


}
