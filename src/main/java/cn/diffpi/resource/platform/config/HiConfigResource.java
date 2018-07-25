package cn.diffpi.resource.platform.config;

import cn.diffpi.resource.platform.config.model.HiConfig;
import cn.dreampie.route.annotation.API;

/**
 * @author: superhe
 * @description:
 * @date: 23:22 2018/7/25
 */
@API("/config")
public class HiConfigResource {

	/**
	 * 更新配置
	 * @param name 配置名称
	 * @param value 配置内容
	 * @return
	 */
	public boolean updateConfig(String name, String value) {
		return HiConfig.dao.updateColsBy("value","name = ?",value, name);
	}

}
