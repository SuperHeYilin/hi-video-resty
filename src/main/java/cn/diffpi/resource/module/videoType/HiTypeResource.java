package cn.diffpi.resource.module.videoType;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.videoType.model.HiType;
import cn.dreampie.route.annotation.*;

import java.util.List;

/**
 * Author: super
 * Date:   2018/7/31 14:25
 */
@API("/video-type")
public class HiTypeResource extends ApiResource {

    /**
     * 所以类型
     *
     * @return
     */
    @GET
    public List<HiType> listAll() {
        return HiType.dao.findAll();
    }

    /**
     * 通过id找到某个类型
     *
     * @param id
     * @return
     */
    @GET("/id")
    public HiType findById(int id) {
        return HiType.dao.findById(id);
    }

    /**
     * 添加类型
     *
     * @param hiType
     * @return
     */
    @POST
    public boolean add(HiType hiType) {
        return hiType.save();
    }

    /**
     * 更新某个类型
     *
     * @param hiType
     * @return
     */
    @PUT
    public boolean update(HiType hiType) {
        return hiType.update();
    }

    /**
     * 删除类型
     *
     * @param id
     * @return
     */
    @DELETE
    public boolean delete(int id) {
        return HiType.dao.deleteById(id);
    }

}
