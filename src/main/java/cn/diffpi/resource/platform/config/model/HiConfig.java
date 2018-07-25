package cn.diffpi.resource.platform.config.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

/**
 * @author: superhe
 * @description:
 * @date: 23:21 2018/7/25
 */
@Table(name = "hi_config")
public class HiConfig extends BaseModel<HiConfig> {
	public static final HiConfig dao = new HiConfig();

	/**
	 * 获取删除文件目录
	 * @return
	 */
	public String getDeletePath() {
		HiConfig hiConfig = HiConfig.dao.findFirstBy("name = deletePath");
		String path = hiConfig.get("value");
		return path;
	}

}
