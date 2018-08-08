package cn.diffpi.core.quartz;

import java.text.ParseException;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;

import cn.dreampie.quartz.Quartzer;

/**
 * 调度器，用于作业的添加、删除、启动
 * kkk
 *
 * @author lb
 * @date:2017年7月24日09:55:08
 */
public class ScheduleService {

    private Scheduler scheduler; // 作业调度器

    public Scheduler getScheduler() {
        return scheduler;
    }

    public ScheduleService() {
        try {
            scheduler = Quartzer.getSchedulerFactory().getScheduler();
        } catch (SchedulerException e) {
            System.err.println("初始化获取作业调度器失败");
            e.printStackTrace();
        }
    }

    /**
     * 删除触发器
     *
     * @param triggerName
     * @param group
     * @return
     * @throws Exception
     * @Title: removeTrigdger
     * @Description: TODO
     * @return: boolean
     */
    public boolean removeTrigdger(String triggerName, String group)
            throws Exception {
        TriggerKey triggerKey = geTriggerKey(triggerName, group);
        try {
            scheduler.pauseTrigger(triggerKey); // 停止触发器
            return scheduler.unscheduleJob(triggerKey); // 移除触发器
        } catch (SchedulerException e) {
            throw new Exception("删除作业时发生异常", e);
        }
    }

    /**
     * 重启作业
     *
     * @param triggerName
     * @param group
     * @throws Exception
     * @Title: resumeTrigger
     * @Description: TODO
     * @return: void
     */
    public void resumeTrigger(String triggerName, String group)
            throws Exception {
        TriggerKey triggerKey = geTriggerKey(triggerName, group);

        try {
            scheduler.resumeTrigger(triggerKey);
        } catch (SchedulerException e) {
            throw new Exception("重启作业时发生异常", e);
        }
    }

    /**
     * 暂停作业
     *
     * @param triggerName
     * @param group
     * @throws Exception
     * @Title: pauseTrigger
     * @Description: TODO
     * @return: void
     */
    public void pauseTrigger(String triggerName, String group) throws Exception {
        TriggerKey triggerKey = geTriggerKey(triggerName, group);

        try {
            scheduler.pauseTrigger(triggerKey);
        } catch (SchedulerException e) {
            throw new Exception("暂停作业是发生异常", e);
        }
    }

    /**
     * 新增作业
     *
     * @param jobName        作业名称
     * @param group          作业和触发器所在分组
     * @param job_class_name 作业的类名称，必须是全限定类名 如：com.acca.xxx.service（不能是接口）
     * @param triggerName    触发器名称
     * @param cronExpression 时间规则
     * @param description    描述
     * @throws ParseException
     * @throws ClassNotFoundException
     * @throws SchedulerException
     * @Title: scheduleJob
     * @Description: TODO
     * @return: void
     */
    public void scheduleJob(String jobName, String group,
                            String job_class_name, String triggerName, String cronExpression,
                            String description) throws ParseException, ClassNotFoundException,
            SchedulerException {
        try {
            JobDetail jobDetail = getJobDetail(jobName, group, job_class_name,
                    description);
            Trigger trigger = getTrigger(triggerName, group, cronExpression,
                    description);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (ParseException e) {
            throw e;
        } catch (SchedulerException e) {
            throw e;
        }
    }

    /**
     * 更新触发器的时间规则
     *
     * @param triggerName    触发器名称
     * @param group          触发器分组
     * @param cronExpression 时间规则
     * @throws ParseException     解析异常
     * @throws SchedulerException 调度器异常
     * @Title: rescheduleJob
     * @Description: TODO
     * @return: void
     */
    public void rescheduleJob(String triggerName, String group,
                              String cronExpression) throws ParseException, SchedulerException {
        TriggerKey triggerKey = geTriggerKey(triggerName, group);

        try {
            Trigger newTrigger = getTrigger(triggerName, group, cronExpression);
            scheduler.rescheduleJob(triggerKey, newTrigger);
        } catch (ParseException e) {
            throw e;
        } catch (SchedulerException e) {
            throw e;
        }
    }

    /**
     * 立即执行作业，不考虑时间规则
     *
     * @param jobName 作业名称
     * @param group   作业分组
     * @throws SchedulerException 调度器异常
     * @Title: triggerJob
     * @Description: TODO
     * @return: void
     */
    public void triggerJob(String jobName, String group)
            throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, group);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new SchedulerException("立即执行作业时发生异常", e);
        }
    }

    /**
     * 获取触发器
     *
     * @param triggerName    触发器名称
     * @param group          触发器分组
     * @param cronExpression 触发器时间规则
     * @return
     * @throws ParseException 解析异常
     * @Title: getTrigger
     * @Description: TODO
     * @return: Trigger
     */
    private Trigger getTrigger(String triggerName, String group,
                               String cronExpression) throws ParseException {
        CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);

        try {
            triggerImpl.setCronExpression(cronExpression);
        } catch (ParseException e) {
            throw e;
        }

        return triggerImpl;
    }

    /**
     * 获取触发器的key
     *
     * @param triggerName
     * @param group
     * @return
     * @Title: geTriggerKey
     * @Description: TODO
     * @return: TriggerKey
     */
    private TriggerKey geTriggerKey(String triggerName, String group) {
        TriggerKey triggerKey = new TriggerKey(triggerName, group);

        return triggerKey;
    }

    /**
     * 创建Jobdetail类
     *
     * @param jobName        作业名称
     * @param group          作业所在组
     * @param job_class_name 作业的权限定类名
     * @param description
     * @return
     * @throws ClassNotFoundException
     * @Title: getJobDetail
     * @Description: TODO
     * @return: JobDetail
     */
    private JobDetail getJobDetail(String jobName, String group,
                                   String job_class_name, String description)
            throws ClassNotFoundException {
        try {
            JobDetailImpl detail = new JobDetailImpl(jobName, group,
                    (Class<? extends Job>) Class.forName(job_class_name));
            detail.setDescription(description);

            return detail;
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(job_class_name + "不存在或者书写不正确", e);
        }
    }

    /**
     * 创建触发器
     *
     * @param triggerName    触发器名称
     * @param group          触发器所在组
     * @param cronExpression 触发器的时间规则
     * @return
     * @throws ParseException 解析时间规则异常
     * @Title: getTrigger
     * @Description: TODO
     * @return: Trigger
     */
    private Trigger getTrigger(String triggerName, String group,
                               String cronExpression, String description) throws ParseException {
        CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
        triggerImpl.setDescription(description);

        try {
            triggerImpl.setCronExpression(cronExpression);
        } catch (ParseException e) {
            throw e;
        }

        return triggerImpl;
    }
}
