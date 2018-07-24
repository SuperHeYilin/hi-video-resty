package cn.diffpi.resource.module.video.model;

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

    /**
     * @return 所有重复视频
     */
    public List<HiVideo> findAllRepeat() {
	    List<HiVideo> first = new ArrayList<>();
        first = HiVideo.dao.find("SELECT * FROM hi_video a,(SELECT size_b FROM hi_video GROUP BY size_b HAVING count(size_b) > 1 ) b WHERE a.size_b = b.size_b ORDER BY a.size_b");
	    return first;
    }

    /**
     * 第一个重复
     * @return
     */
    public List<HiVideo> findFirstRepeat() {
        List<HiVideo> first = new ArrayList<>();
        first = HiVideo.dao.find("SELECT * FROM hi_video a,(SELECT size_b FROM hi_video GROUP BY size_b HAVING count(size_b) > 1 limit 0,1 ) b WHERE a.size_b = b.size_b ORDER BY a.size_b");
        return first;
    }

}
