package cn.diffpi.resource.module.videoType.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: super
 * Date:   2018/7/31 14:24
 */
@Table(name = "hi_type")
public class HiType extends BaseModel<HiType> {
    public static final HiType dao = new HiType();

    /**
     * 同过简写集合找到类型名称
     *
     * @param names
     * @return
     */
    public List<String> findTypeName(String names) {
        List<String> list = new ArrayList<>();
        // 获取类型简写
        if (names == null) {
            return null;
        }
        for (String s : names.split(",")) {
            HiType hiVideoType = HiType.dao.findFirstBy("key_name = ?", s);
            if (hiVideoType != null) {
                list.add(hiVideoType.get("name", String.class));
            }
        }
        return list;
    }

}
