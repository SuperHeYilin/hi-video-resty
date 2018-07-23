package cn.diffpi.resource.module.video.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

import java.io.File;
import java.util.List;

/**
 * @author: superhe
 * @description:
 * @date: 22:51 2018/7/21
 */
@Table(name = "hi_video")
public class HiVideo extends BaseModel<HiVideo> {
	public static final HiVideo dao = new HiVideo();

	/**
	 * 初始化导入视频信息
	 * @param fileList
	 * @return
	 */
	public boolean initVideo(List<String> fileList) {
		// 文件路径名 便利
		for (String s : fileList) {
			File file = new File(s);
			// 如果文件存在
			if (file.exists()) {
				HiVideo hiVideo = new HiVideo();
				hiVideo
								.set("score", 1500)
								.set("file_name", file.getName())
								.set("path", file.getPath())
								.set("size_mb", file.length() / 1024 / 1024)
								.set("size_b", file.length())
								.set("state", 0)
								.save();
			}
		}
		return true;
	}

}
