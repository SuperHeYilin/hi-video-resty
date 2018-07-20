package cn.diffpi.resource.job;

import cn.diffpi.resource.module.order.model.Order;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class OrderJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Order.dao.closeOrder();
    }
}
