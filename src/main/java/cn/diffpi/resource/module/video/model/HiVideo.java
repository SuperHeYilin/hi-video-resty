package cn.diffpi.resource.module.video.model;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.video.ELOUtil;
import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;
import cn.dreampie.route.core.Params;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: superhe
 * @description:
 * @date: 22:51 2018/7/21
 */
@Table(name = "hi_video")
public class HiVideo extends BaseModel<HiVideo> {
	public static final HiVideo dao = new HiVideo();

	/**
	 * 根据参数 拼装sql
	 * @param arrayList
	 * @param p
	 * @return
	 * */
	public String getSql(ArrayList<Object> arrayList, Params p) {
		StringBuilder sql = new StringBuilder("from hi_video where is_del = 0 ");

		// 处理状态
		String fileName = p.get("file_name");
		if(StrKit.notBlank(fileName)) {
			sql.append("and file_name like '%");
			sql.append(fileName);
			sql.append("%' ");
			arrayList.add(fileName);
		}

		return sql.toString();
	}

	/**
	 * 随机两条数据
	 * @param type 随机类型 all所有随机
	 * @param id 雷主id
	 * @return
	 */
	public Map<String, Object> getRandVideo(String type, int id) {
		Map<String, Object> map = new HashMap<>();
		HiVideo a, b;
		int aId, bId;

		// 如果所有随机
		if ("all".equals(type)) {
			a = findRand();
			System.out.println(a);
			aId = a.get("id", Integer.class);
			do {
				b = findRand();
				System.out.println(b);
				bId = b.get("id", Integer.class);
			} while (aId == bId);
		} else {
			// 通过id找到固定不变的那条数据
			a = dao.findById(id);
			do {
				b = findRand();
				bId = b.get("id", Integer.class);
			} while (id == bId);
		}
		// 胜率期望值
		int aScore = a.get("score", Integer.class);
		int bScore = b.get("score", Integer.class);
		double winDHope = ELOUtil.winHope(aScore, bScore);
		Integer winHope = Integer.parseInt(new java.text.DecimalFormat("0").format(winDHope * 100));

		List<HiVideo> list = dao.find("SELECT * from hi_video ORDER BY score DESC LIMIT 0, 20");

		map.put("a", a);
		map.put("b", b);
		map.put("winHope", winHope);
		map.put("range", list);
		return map;
	}

	/**
	 * 得到一条随机记录
	 * @return
	 */
	public HiVideo findRand() {
		HiVideo hiVideo = dao.findFirst("SELECT * FROM hi_video as a JOIN\n" +
				"\t(SELECT ROUND(RAND()*((SELECT MAX(id) from hi_video)-(SELECT MIN(id) from hi_video))+(SELECT MIN(id) FROM hi_video AS id)) as bid) as b\n" +
				"\t\tWHERE a.id >= b.bid ORDER BY a.id");
		return hiVideo;
	}

	/**
	 * 初始化导入视频信息
	 * @param fileList
	 * @return
	 */
	public boolean initVideo(List<String> fileList) {
		// 文件路径名 便利
		for (String s : fileList) {
			System.out.println("wwwwwwwwwwww " + s);
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
								.set("create_date", DateUtil.getCurrentDate())
								.set("is_del", "0")
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
