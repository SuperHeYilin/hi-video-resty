package cn.diffpi.resource.module.Video.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: superhe
 * @description:
 * @date: 22:51 2018/7/21
 */
@Table(name = "hi_video")
public class HiVideo extends BaseModel<HiVideo> {
	public static final HiVideo dao = new HiVideo();

	public boolean initVideo(String path) {

		List<String> list = new ArrayList<>();

		// 文件路径名 便利
		for (String s : list) {
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
				// 父级目录
				String parentPath = file.getParent();
			}
		}

		return true;
	}

}
