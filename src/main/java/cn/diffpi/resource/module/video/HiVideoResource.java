package cn.diffpi.resource.module.video;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.StringKit;
import cn.diffpi.kit.video.ELOUtil;
import cn.diffpi.kit.video.FileUtil;
import cn.diffpi.kit.video.ScanFileUtil;
import cn.diffpi.kit.video.StringUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.directory.model.HiDirectory;
import cn.diffpi.resource.module.video.model.HiVideo;
import cn.diffpi.resource.module.videoType.model.HiType;
import cn.dreampie.orm.transaction.Transaction;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

import java.io.File;
import java.util.*;

/**
 * 视频资源类
 *
 * @author: superhe
 * @description:
 * @date: 22:56 2018/7/21
 */
@API("/video")
public class HiVideoResource extends ApiResource {


    @GET
    public SplitPage listWithdrawals() {
        SplitPage page = getModel(SplitPage.class, true);
        // 可变占位符参数对象集合
        ArrayList<Object> arrayList = new ArrayList<>();
        // 通过参数获取动态sql
        String sql = HiVideo.dao.getSql(arrayList, getParams());

        HiVideo.dao.splitPageBaseSql(page, "select * ", sql, arrayList.toArray());

        return page;
    }

    /**
     * 通过视频id找到视频
     *
     * @param id
     * @return
     */
    @GET("/detail")
    public Map<String, Object> getById(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        String parentPath;
        // 视频信息
        HiVideo videoInfo = HiVideo.dao.findFirstBy("id = ? and is_del = 0", id);
        map.put("videoInfo", videoInfo);

        // 视频名称关键字建议
        Set<String> set = StringUtil.split(videoInfo.get("file_name", String.class));
        map.put("videoKey", set);
        // 父级目录
        String path = videoInfo.get("path");
        File file = new File(path);
        // 父级目录名称
        parentPath = file.getParentFile().getName();
        map.put("parentPath", parentPath);

        // 当前视频类型 key类型 name型
        String videoType = videoInfo.get("video_type");
        if (StringKit.isNotBlank(videoType)) {
            String[] typeList = videoType.split(",");
            map.put("typeList", typeList);

            List<String> typeNameList = HiType.dao.findTypeName(videoType);
            map.put("typeNameList", typeNameList);
        }

        // 所有类型
        List<HiType> allTypeList = HiType.dao.findAll();
        map.put("allTypeList", allTypeList);

        return map;
    }

    /**
     * 获取pk数据
     *
     * @param type
     * @param id
     * @return
     */
    @GET("/rand")
    public Map<String, Object> getRandVideo(String type, int id) {
        return HiVideo.dao.getRandVideo(type, id);
    }

    /**
     * 改变等级分
     *
     * @param aId
     * @param bId
     * @param score
     */
    @POST("/change-score")
    @Transaction
    public void changeScore(int aId, int bId, double score) {
        HiVideo a = HiVideo.dao.findById(aId);
        HiVideo b = HiVideo.dao.findById(bId);
        int aOldScore = a.get("score", Integer.class);
        int bOldScore = b.get("score", Integer.class);
        Map<String, Integer> map = ELOUtil.countRange(aOldScore, bOldScore, score);
        int aScore = map.get("a");
        int bScore = map.get("b");

        a
                .set("score", aScore)
                .set("update_date", DateUtil.getCurrentDate())
                .update();
        b
                .set("score", bScore)
                .set("update_date", DateUtil.getCurrentDate())
                .update();
    }

    /**
     * 通过id打开视频
     *
     * @param id
     * @return
     */
    @GET("/play")
    public boolean playVideo(int id) {
        HiVideo hiVideo = HiVideo.dao.findFirstBy("id = ? and is_del = 0", id);
        if (hiVideo != null) {
            String path = hiVideo.get("path");
            FileUtil.openFile(path);
            return true;
        }
        return false;
    }

    /**
     * 视频初始化导入 初始化目录
     *
     * @param path
     * @return
     */
    @POST("/init")
    public boolean initVideo(String path) {
        Map<String, Object> testMap = new ScanFileUtil().listFiles(path);
        List<String> fileList = (List<String>) testMap.get("files");
        List<Map<String, Object>> dirList = (List<Map<String, Object>>) testMap.get("directories");

        HiVideo.dao.initVideo(fileList);

        HiDirectory.dao.initDirectory(dirList);

        return true;
    }

    /**
     * @return 重复视频数据
     */
    @GET("/repeat")
    public Map<String, Object> repeat() {
        Map<String, Object> map = new HashMap<>();

        List<HiVideo> allRepeat = HiVideo.dao.findAllRepeat();

        List<HiVideo> firstRepeat = HiVideo.dao.findFirstRepeat();

        map.put("allRepeat", allRepeat);

        map.put("firstRepeat", firstRepeat);

        return map;
    }

    /**
     * 更新视频类型
     *
     * @param id
     * @param videoType
     * @return
     */
    @PUT
    public boolean update(int id, String videoType) {
        HiVideo hiVideo = HiVideo.dao.findById(id);
        return hiVideo
                .set("video_type", videoType)
                .set("update_date", DateUtil.getCurrentDate())
                .update();
    }

}
