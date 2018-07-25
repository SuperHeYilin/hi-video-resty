package cn.diffpi.resource.file;

import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.video.FileUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.video.model.HiVideo;
import cn.diffpi.resource.platform.config.model.HiConfig;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

import java.util.List;

/**
 * @author super
 * @date 2018/7/24 15:11
 */
@API("/file")
public class FileResource extends ApiResource {
    /**
     * 打开文件
     * @param ids 路径
     */
    @POST("/open")
    public void openVideo(String ids) {
        if (StrKit.notBlank(ids)) {
            for (String id : ids.split(",")) {
                HiVideo hiVideo = HiVideo.dao.findById(id);
                String path = hiVideo.get("path");
                if (StrKit.notBlank(path)) {
                    FileUtil.openFile(path);
                }
            }
        }
    }

    /**
     * 移动删除  移动到指定目录
     * @param ids
     * @return
     */
    @DELETE("/delete")
    public boolean deleteVideo(String ids) {
        if (StrKit.notBlank(ids)) {
            for (String id : ids.split(",")) {
                HiVideo hiVideo = HiVideo.dao.findById(id);
                String path = hiVideo.get("path");
                if (StrKit.notBlank(path)) {
                    // 获取配置的删除目录路径
                    String deletePath = HiConfig.dao.getDeletePath();
                    FileUtil.moveFile(path, deletePath);
                    hiVideo.delete();
                }
            }
        }
        return true;
    }
}
