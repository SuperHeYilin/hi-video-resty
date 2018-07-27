package cn.diffpi.resource.file;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.video.FileUtil;
import cn.diffpi.kit.video.ScanFileUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.video.model.HiVideo;
import cn.diffpi.resource.platform.config.model.HiConfig;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.core.multipart.FILE;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件请求类
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
     * 为当前文件创建父级目录
     * @param path  文件目录
     * @param dirName   新建目录名称
     * @return
     */
    @POST("/parent-dir")
    public boolean addParentDir(String path, String dirName) {
        // 找到父级目录
        String parentDir = FileUtil.findParent(path);
        // 创建新目录
        return FileUtil.newFolder(parentDir + File.separator + dirName);
    }

    /**
     * 移动截图到当前视频目录下
     * @param targetDirectory
     * @return
     */
    @POST("/catch-img")
    public boolean catchScreenImg(String targetDirectory) {
        // 截图地址
        String screenImgPath = HiConfig.dao.getConfigValue("screenPath");
        if (screenImgPath != null) {
            // 截图地址文件夹对象
            File file = new File(screenImgPath);
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files == null) {
                    return false;
                }
                if (files.length == 0) {
                    System.out.println("文件夹是空的!");
                } else {
                    for (File file2 : files) {
                        // 不为文件夹 就重命名 移动
                        if (!file2.isDirectory()) {
                            // 图片名称
                            String imgName = file2.getName();
                            // 图片后缀
                            String suffix = imgName.substring(imgName.lastIndexOf(".")).toLowerCase();
                            // 父级目录
                            String parentPath = file2.getParent();
                            // 新目标对象
                            File newFile = new File(targetDirectory + File.separator + System.currentTimeMillis() + suffix);
                            // 重命名移动文件
                            if (file2.renameTo(newFile)) {
                                return true;
                            }
                            // 新路径
                            String newPath = file2.getAbsolutePath();
                            // todo 添加图片到数据库
                        }
                    }
                }
            } else {
                System.out.println("文件不存在!");
                return false;
            }
            return true;
        }
        return false;
    }
    /**
     * 移动删除  移动到指定目录    回收站
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
                    String deletePath = HiConfig.dao.getConfigValue("deletePath");
                    FileUtil.moveFile(path, deletePath);
                    hiVideo.delete();
                }
            }
        }
        return true;
    }

}
