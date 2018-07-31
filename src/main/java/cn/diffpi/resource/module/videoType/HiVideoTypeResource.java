package cn.diffpi.resource.module.videoType;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.videoType.model.HiVideoType;
import cn.dreampie.route.annotation.*;

import java.util.List;

/**
 * Author: super
 * Date:   2018/7/31 14:25
 */
@API("/video-type")
public class HiVideoTypeResource extends ApiResource {

    /**
     * 所以类型
     * @return
     */
    @GET
    public List<HiVideoType> listAll() {
        return HiVideoType.dao.findAll();
    }

    /**
     * 通过id找到某个类型
     * @param id
     * @return
     */
    @GET("/id")
    public HiVideoType findById(int id) {
        return HiVideoType.dao.findById(id);
    }

    /**
     * 添加类型
     * @param hiVideoType
     * @return
     */
    @POST
    public boolean add(HiVideoType hiVideoType) {
        return hiVideoType.save();
    }

    /**
     * 更新某个类型
     * @param hiVideoType
     * @return
     */
    @PUT
    public boolean update(HiVideoType hiVideoType) {
        return hiVideoType.update();
    }

    /**
     * 删除类型
     * @param id
     * @return
     */
    @DELETE
    public boolean delete(int id) {
        return HiVideoType.dao.deleteById(id);
    }

}
