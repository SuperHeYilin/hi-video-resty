package cn.diffpi.resource.platform.config.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.orm.annotation.Table;
import cn.dreampie.route.annotation.GET;

import java.util.List;

/**
 * @author: superhe
 * @description:
 * @date: 23:21 2018/7/25
 */
@Table(name = "hi_config")
public class HiConfig extends BaseModel<HiConfig> {
    public static final HiConfig dao = new HiConfig();

    /**
     * 配置查找
     *
     * @param keyName 根据键找到值
     * @return
     */
    public String getConfigValue(String keyName) {
        HiConfig hiConfig = HiConfig.dao.findFirstBy("key_name = ?", keyName);
        if (hiConfig == null) {
            return "";
        }
        return hiConfig.get("value");
    }


}
