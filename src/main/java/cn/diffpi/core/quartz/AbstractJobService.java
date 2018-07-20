package cn.diffpi.core.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 * 抽象Job 类，如果某个类需要实现定时作业功能，可以继承此类并重写抽象方法
 * 
 * @author lb
 * @date: 2017年7月24日10:16:44
 */
public abstract class AbstractJobService implements Job
{

    public final void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        beforeExecute(context); 
        doExecute(context);
        afterExecute(context);
    }

    // 执行前的操作
    protected void beforeExecute(JobExecutionContext context)
    { 
    }

    // 执行后的操作
    protected void afterExecute(JobExecutionContext context)
    {

    }

    // 具体执行逻辑交由子类实现
    protected abstract void doExecute(JobExecutionContext context);

}
