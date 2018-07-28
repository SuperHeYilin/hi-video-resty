package cn.diffpi.resource.module.video;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.video.FileUtil;
import cn.diffpi.kit.video.ScanFileUtil;
import cn.diffpi.kit.video.StringUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.video.model.HiVideo;
import cn.diffpi.resource.module.directory.model.HiDirectory;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.core.Params;

import java.util.*;

/**
 * 视频资源类
 * @author: superhe
 * @description:
 * @date: 22:56 2018/7/21
 */
@API("/video")
public class HiVideoResource extends ApiResource {


	@GET
	public SplitPage listWithdrawals() {
		SplitPage page = getModel(SplitPage.class,true);
		// 可变占位符参数对象集合
		ArrayList<Object> arrayList = new ArrayList<>();
		// 通过参数获取动态sql
		String sql = HiVideo.dao.getSql(arrayList, getParams());

		HiVideo.dao.splitPageBaseSql(page, "select * ", sql, arrayList.toArray());

		return page;
	}

	/**
	 * 通过视频id找到视频
	 * @param id
	 * @return
	 */
	@GET("/detail")
	public Map<String, Object> getById(int id) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 视频信息
		HiVideo videoInfo = HiVideo.dao.findFirstBy("id = ? and is_del = 0", id);
		map.put("videoInfo", videoInfo);

		// 视频名称关键字建议
		Set<String> set = StringUtil.split(videoInfo.get("file_name", String.class));
		map.put("videoKey", set);

		return map;
	}

	/**
	 * 通过id打开视频
	 * @param id
	 * @return
	 */
	@GET("/play")
	public boolean playVideo(int id) {
		HiVideo hiVideo = HiVideo.dao.findFirstBy("id = ? and is_del = 0", id);
		if (hiVideo != null) {
			String path = hiVideo.get("path");
			FileUtil.openFile(path);
			return true;
		}
		return false;
	}

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
