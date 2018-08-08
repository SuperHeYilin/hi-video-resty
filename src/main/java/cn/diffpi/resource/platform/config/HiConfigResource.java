package cn.diffpi.resource.platform.config;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.config.model.HiConfig;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

import java.util.List;

/**
 * @author: superhe
 * @description:
 * @date: 23:22 2018/7/25
 */
@API("/config")
public class HiConfigResource extends ApiResource {
    /**
     * 罗列所有配置
     *
     * @return
     */
    @GET
    public List<HiConfig> getAllConfig() {
        return HiConfig.dao.findAll();
    }

    /**
     * 添加配置
     *
     * @param hiConfig
     * @return
     */
    @POST
    public boolean addConfig(HiConfig hiConfig) {
        return hiConfig.save();
    }

    /**
     * 更新配置
     *
     * @param hiConfig 配置
     * @return
     */
    @PUT
    public boolean updateConfig(HiConfig hiConfig) {
        return hiConfig.update();
    }

}
