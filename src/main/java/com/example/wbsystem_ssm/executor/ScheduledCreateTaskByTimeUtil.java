//package com.example.wbsystem_ssm.executor;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ScheduledFuture;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// *         Java组件实现实现动态创建、删除定时任务
// * @author Sentiment1996
// *
// */
//public class ScheduledCreateTaskByTimeUtil {
//
//        private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
//        /**
//         *         任务存储中心
//         */
//        private static Map<String ,Object> map = new HashMap<String,Object>();
//
//        static void init() {
//                scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(100);
//        }
//
//        /**
//         *         删除定时任务
//         * @param className
//         * @return
//         */
//        public static void removeScheduled(String className) {
//                if(map.get(className)==null) {
//                        return;
//                }else{
//                        scheduledThreadPoolExecutor.remove((Runnable) map.get(className));
//                }
//        }
//
//        /**
//         *         创建 / 更新定时任务
//         * @param className
//         * @param initialDelay
//         * @param period
//         * @param timeUnit
//         */
//        public static void createScheduled(String className,TaskFactory taskFactory) {
//                if(scheduledThreadPoolExecutor==null) {
//                        init();
//                }
//                removeScheduled(className);
//                // 创建定时器
//                ScheduledFuture<?> scheduledFuture = scheduledThreadPoolExecutor.scheduleWithFixedDelay(taskFactory, taskFactory.initialDelay, taskFactory.period, taskFactory.timeUnit);
//                // 添加到map缓存器
//                map.put(className,scheduledFuture);
//        }
//
//        /**
//         *         初始化对象参数
//         * @param taskFactory
//         * @param initialDelay
//         * @param period
//         * @param timeUnit
//         */
//        public static TaskFactory initObjectParam(String className,String initialDelay,String period,String timeUnit) {
//                // 创建对象
//                TaskFactory taskFactory = getObjectClass(className);
//                // 赋参
//                taskFactory.initialDelay = Long.parseLong(initialDelay);
//                taskFactory.period = Long.parseLong(period);
//                taskFactory.timeUnit = getTimeUnit(timeUnit);
//                return taskFactory;
//        }
//
//        /**
//         *         获取TimeUnit对象
//         * @param timeUnit
//         * @return
//         */
//        private static TimeUnit getTimeUnit(String timeUnit) {
//                switch(timeUnit) {
//                case "days":
//                        return TimeUnit.DAYS;
//                case "hours":
//                        return TimeUnit.HOURS;
//                case "minutes":
//                        return TimeUnit.MINUTES;
//                case "seconds":
//                        return TimeUnit.SECONDS;
//                case "milliseconds":
//                        return TimeUnit.MILLISECONDS;
//                default:
//                        return TimeUnit.MILLISECONDS;
//                }
//        }
//
//        /**
//         *         动态创建对象
//         * @param className
//         * @return
//         */
//        private static TaskFactory getObjectClass(String className) {
//                try {
//                        new TaskFactory();
//                        return (TaskFactory) Class.forName(className).newInstance();
//                } catch (InstantiationException e) {
//                        e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                } catch (NoClassDefFoundError e) {
//                        e.printStackTrace();
//                }
//                return null;
//        }
//}