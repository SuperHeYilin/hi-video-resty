package cn.diffpi.resource.platform.quartz.model;

import java.util.HashMap;
import java.util.Map;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

/***
 * Created by one__l on 2016年8月18日
 */
@Table(name = "qrtz_triggers")
public class PtTaskQuartz extends BaseModel<PtTaskQuartz> {
	public final static PtTaskQuartz dao = new PtTaskQuartz();
	public static final Map<String, String> STATE = new HashMap<String, String>(); 
	static {  
		STATE.put("WAITING", "等待");
		STATE.put("PAUSED", "暂停");
		STATE.put("ACQUIRED", "正常执行");
		STATE.put("BLOCKED", "阻塞");
		STATE.put("ERROR", "错误");
    }
	public void getupstate(){
		String state = this.get("TRIGGER_STATE");
		this.set("TRIGGER_STATE", STATE.get(state));
	}
}