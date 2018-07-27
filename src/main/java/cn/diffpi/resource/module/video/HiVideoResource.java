package cn.diffpi.resource.module.video;

import cn.diffpi.kit.video.ScanFileUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.video.model.HiVideo;
import cn.diffpi.resource.module.directory.model.HiDirectory;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频资源类
 * @author: superhe
 * @description:
 * @date: 22:56 2018/7/21
 */
@API("/video")
public class HiVideoResource extends ApiResource {

	/**
	 * 视频初始化导入 初始化目录
	 * @param path
	 * @return
	 */
	@POST("/init")
	public boolean initVideo(String path) {
		Map<String, Object> testMap = ScanFileUtil.listFiles(path);
		List<String> fileList = (List<String>) testMap.get("files");
		List<Map<String, Object>> dirList = (List<Map<String, Object>>) testMap.get("directories");

		HiVideo.dao.initVideo(fileList);

		HiDirectory.dao.initDirectory(dirList);

		return true;
	}

	/**
	 * @return 重复视频数据
	 */
	@GET("/repeat")
	public Map<String, Object> repeat() {
		Map<String, Object> map = new HashMap<>();

		List<HiVideo> allRepeat = HiVideo.dao.findAllRepeat();

		List<HiVideo> firstRepeat = HiVideo.dao.findFirstRepeat();

		map.put("allRepeat", allRepeat);

		map.put("firstRepeat", firstRepeat);

		return map;
	}

}
