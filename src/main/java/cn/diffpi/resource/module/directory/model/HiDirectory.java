package cn.diffpi.resource.module.directory.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

import java.util.List;
import java.util.Map;

/**
 * @author: superhe
 * @description:
 * @date: 22:23 2018/7/21
 */
@Table(name = "hi_directory")
public class HiDirectory extends BaseModel<HiDirectory> {
	public static final HiDirectory dao = new HiDirectory();

	/**
	 * 初始化目录结构
	 * @param dirList
	 * @return
	 */
	public boolean initDirectory(List<Map<String, Object>> dirList) {
		for (Map<String, Object> map : dirList) {
			HiDirectory hiDirectory = new HiDirectory();
			hiDirectory
							.set("id", map.get("id"))
							.set("p_id", map.get("fatherId"))
							.set("name", map.get("fileName"))
							.set("path", map.get("path"))
							.save();
		}
		return true;
	}


}
