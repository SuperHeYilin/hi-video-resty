package cn.diffpi.resource.platform.quartz;

import java.text.ParseException;

import org.quartz.SchedulerException;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.core.quartz.ScheduleService;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.quartz.model.PtTaskQuartz;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

/**
 * Quzrtz管理类
 *
 * @author LB
 */
@API("/pt/quartz")
public class PtTaskQuartzResource extends ApiResource {
    public static final String SELECT_QRTZ_SQL = "select tr.*,cr.CRON_EXPRESSION,jo.JOB_CLASS_NAME ";
    public static final String WHERE_QRTZ_SQL = "from qrtz_triggers tr,QRTZ_CRON_TRIGGERS cr,QRTZ_JOB_DETAILS jo where cr.SCHED_NAME=tr.SCHED_NAME and cr.TRIGGER_NAME=tr.TRIGGER_NAME and cr.TRIGGER_GROUP=tr.TRIGGER_GROUP  and jo.SCHED_NAME=tr.SCHED_NAME and jo.JOB_NAME=tr.JOB_NAME and jo.JOB_GROUP=tr.JOB_GROUP";

    public static ScheduleService ss = new ScheduleService();

    /**
     * 获取所有Quartz任务
     *
     * @return
     */
    @GET
    public SplitPage getAllQuartz() {
        SplitPage page = getModel(SplitPage.class, true);
        PtTaskQuartz.dao.splitPageBaseSql(page, SELECT_QRTZ_SQL, WHERE_QRTZ_SQL);
        return page;
    }

    /**
     * 重启指定任务
     *
     * @param group       作业和触发器所在分组
     * @param triggerName 触发器名称
     * @return
     */
    @POST("/start/:group/:triggerName")
    public boolean startQuartz(String group, String triggerName) {
        try {
            ss.resumeTrigger(triggerName, group);
        } catch (Exception e) {
            System.err.println("重启任务失败");
            return false;
        }
        return true;

    }

    /**
     * 暂停指定任务
     *
     * @param group       作业和触发器所在分组
     * @param triggerName 触发器名称
     * @return
     */
    @POST("/stop/:group/:triggerName")
    public boolean stopQuartz(String group, String triggerName) {
        try {
            ss.pauseTrigger(triggerName, group);
        } catch (Exception e) {
            System.err.println("暂停任务失败");
            return false;
        }
        return true;
    }

    /**
     * 立即执行任务
     *
     * @param group       作业和触发器所在分组
     * @param triggerName 触发器名称
     * @return
     */
    @POST("/hand/:group/:jobName")
    public boolean handQuartz(String group, String jobName) {
        try {
            ss.triggerJob(jobName, group);
        } catch (Exception e) {
            System.err.println("执行任务失败");
            return false;
        }
        return true;
    }

    /**
     * 新增任务
     *
     * @return
     */
    @POST("/addquartz")
    public boolean addQuartzs() {
        String jobName = getParam("jobName") + "";//作业名称
        String group = getParam("group");// 作业和触发器所在分组
        String job_class_name = getParam("job_class_name");//作业的类名称，必须是全限定类名 如：com.acca.xxx.service（不能是接口）
        String triggerName = getParam("triggerName");//触发器名称
        String cronExpression = getParam("cronExpression");//时间规则
        String description = getParam("description");//描述
        try {
            ss.scheduleJob(jobName, group, job_class_name, triggerName, cronExpression, description);
        } catch (Exception e) {
            System.err.println("新增任务失败");
            return false;
        }
        return true;
    }

    /**
     * 更新任务时间表达式
     *
     * @return
     */
    @PUT("/updatetime")
    public boolean upQuartz() {
        String group = getParam("group");
        String triggerName = getParam("triggerName");
        String cronExpression = getParam("cronExpression");
        try {
            ss.rescheduleJob(triggerName, group, cronExpression);
        } catch (Exception e) {
            System.err.println("更新时间规则失败");
            return false;
        }
        return true;
    }

    /**
     * 删除任务
     *
     * @return
     */
    @DELETE("/del/:group/:triggerName")
    public boolean deleteQuartz(String group, String triggerName) {
        try {
            ss.removeTrigdger(triggerName, group);
        } catch (Exception e) {
            System.err.println("删除作业失败");
        }
        return true;
    }

}
