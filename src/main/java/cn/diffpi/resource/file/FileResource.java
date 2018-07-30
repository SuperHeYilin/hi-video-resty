package cn.diffpi.resource.file;

import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.video.FileUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.video.model.HiVideo;
import cn.diffpi.resource.platform.config.model.HiConfig;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

import java.io.File;

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
     * @param newName   新建目录名称
     * @return
     */
    @POST("/parent-dir")
    public boolean addParentDir(String path, String newName) {
        File file = new File(path);
        // 文件名
        String name = file.getName();
        // 找到父级目录
        String parentDir = file.getParent();
        // 创建新目录
        FileUtil.newFolder(parentDir + File.separator + "test");
        // 移动到新目录
        return file.renameTo(new File(parentDir + File.separator + newName + File.separator + name));
    }

    /**
     * 修改父级目录
     * @param path 文件绝对路径
     * @param newName 名称
     * @return
     */
    @PUT("/parent-dir")
    public boolean updateParentDir(String path, String newName) {
        File file = new File(path);
        // 找到父级目录
        String parentDir = file.getParent();
        // 父级目录对象
        File pFile = new File(parentDir);
        // 获取父父级目录
        String ppDir = pFile.getParent();

        return pFile.renameTo(new File(ppDir + File.separator + newName));

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
                if (hiVideo == null) {
                    throw new HttpException(HttpStatus.NOT_FOUND, "paramErr", id + " 对应的视频不存在！");
                }
                String path = hiVideo.get("path");
                if (StrKit.notBlank(path)) {
                    // 获取配置的删除目录路径
                    String deletePath = HiConfig.dao.getConfigValue("deletePath");
                    if (StrKit.isBlank(deletePath)) {
                        throw new HttpException(HttpStatus.NOT_FOUND,"paramErr", "尚未配置回收站！");
                    }
                    // 移动成功就删除记录
                    if (FileUtil.moveFile(path, deletePath)) {
                        hiVideo.delete();
                    } else {
                        throw new HttpException(HttpStatus.NOT_FOUND,"paramErr", "移动失败！");
                    }
                }
            }
        }
        return true;
    }
}
