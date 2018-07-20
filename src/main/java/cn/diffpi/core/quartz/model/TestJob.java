package cn.diffpi.core.quartz.model;

import org.quartz.JobExecutionContext;

import cn.diffpi.core.quartz.AbstractJobService;
import cn.dreampie.client.Client;
import cn.dreampie.client.ClientResult;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestJob extends AbstractJobService
{
    public static int i = 1;

    @Override
    protected void doExecute(JobExecutionContext context)
    {
        
    }

}
