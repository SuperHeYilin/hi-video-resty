package cn.diffpi.core.quartz;

import java.text.ParseException;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.quartz.SchedulerException;

import cn.dreampie.server.RestyServer;
import cn.dreampie.server.provider.jetty.JettyServerProvider;


public class QuartzManager {
    public static void main(String[] args) throws ClassNotFoundException,
            ParseException, SchedulerException {
        System.out.println(false ? 0 : 1);
//    	RedisCache.instance().add("lb23", "lb_1_2", "asdasdasdsadasds");
//    	Jedis jedis=new Jedis("127.0.0.1", 6379);
//    	Set<String> rawKeys = jedis.keys("_query::default::pt_app_banner::*");
//    	System.out.println(rawKeys.size());
//    	for (String string : rawKeys) {
//    		System.out.println(string);
//		}
//    	jedis.close();
//    	jedis.flushDB();
//    	RedisManager.deleteKeyByPrefix("_query::default::pt_permission::");
//    	RedisManager.destroy();
//    	QueryCache.instance().purge(dsmName, tableName);
//        ScheduleService ss = new ScheduleService();
//        ss.scheduleJob("lb", "lb_one", "cn.diffpi.core.quartz.model.TestJob",
//                "gg1", "* * * * * ? *", "没有描述");
//        ss.getScheduler().start(); 
    }
}
