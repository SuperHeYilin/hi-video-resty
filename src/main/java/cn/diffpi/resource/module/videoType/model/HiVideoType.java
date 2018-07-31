package cn.diffpi.resource.module.videoType.model;

import cn.diffpi.kit.video.StringUtil;
import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: super
 * Date:   2018/7/31 14:24
 */
@Table(name = "hi_video_type")
public class HiVideoType extends BaseModel<HiVideoType> {
    public static final HiVideoType dao = new HiVideoType();

    /**
     * 同过简写集合找到类型对象集合
     * @param name
     * @return
     */
    public List<String> findType(String name) {
//        List<HiVideoType> list = new ArrayList<>();
        // 获取类型简写
        List<String> keyName = StringUtil.getType(name);
        if (keyName == null) {
            return null;
        }
//        for (String s : keyName) {
//            HiVideoType hiVideoType = HiVideoType.dao.findFirstBy("key_name = ?", s);
//            if (hiVideoType != null) {
//                list.add(hiVideoType);
//            }
//        }
        return keyName;
    }

}
