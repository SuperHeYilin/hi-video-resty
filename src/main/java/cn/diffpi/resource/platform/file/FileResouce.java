package cn.diffpi.resource.platform.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import cn.diffpi.resource.ApiResource;
import cn.dreampie.common.http.UploadedFile;
import cn.dreampie.common.util.properties.Proper;
import cn.dreampie.orm.Record;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.core.multipart.FILE;

/**
 * 文件控制类
 *
 * @author lb
 */
@API("/pt/file")
public class FileResouce extends ApiResource {
    /**
     * 获取配置
     *
     * @param file
     * @return
     */
    @GET("/config")
    public Object config() {
        String type = getParam("action");
        if (type != null) {
            switch (type) {
                case "config":
                    return getConfig();
                case "uploadimage":
                    return true;
                case "uploadscrawl":
                    return true;
            }
        }
        return null;
    }

    @POST("/config")
    @FILE(max = 1024 * 1024 * 40)
    public Object upFile(UploadedFile file) {
        Record r = new Record();
        r.put("state", "SUCCESS");
        r.put("title", file.getFileName());
        r.put("original", file.getFileName());
        String type = getParam("action");
        if (type != null) {
            switch (type) {
                case "uploadimage":
                    return r;
                case "uploadscrawl":
                    File fl = ImgKit.savebyfile(file);
                    r.put("url", fl.getName());
                    return r;
                case "uploadvideo":
                    File fl1 = ImgKit.savebyfile(file);
                    r.put("url", "" + fl1.getName());
                    return r;
                default:
                    return r;
            }
        }
        return null;
    }

    public Record getConfig() {
        Record r = new Record();
        /* 上传图片配置项 */
        r.put("imageActionName", "uploadimage"); /* 执行上传图片的action名称 */
        r.put("imageFieldName", "file");/* 提交的图片表单名称 */
        r.put("imageMaxSize", 2048000);/* 上传大小限制，单位B */
        r.put("imageAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif"});/* 上传图片格式显示 */
        r.put("imageCompressEnable", true);/* 是否压缩图片,默认是true */
        r.put("imageCompressBorder", 1600);/* 图片压缩最长边限制 */
        r.put("imageInsertAlign", "none");/* 插入的图片浮动方式 */
        r.put("imageUrlPrefix", "/upload/");/* 图片访问路径前缀 */
        /* 涂鸦图片上传配置项 */
        r.put("imageActionName", "uploadscrawl");/* 执行上传涂鸦的action名称 */
        r.put("scrawlFieldName", "file"); /* 提交的图片表单名称 */
        r.put("scrawlMaxSize", "2048000");/* 上传大小限制，单位B */
        r.put("scrawlUrlPrefix", "/upload/");/* 图片访问路径前缀 */
        r.put("scrawlInsertAlign", "none");
        /* 截图工具上传 */
        r.put("snapscreenActionName", "uploadimage");/* 执行上传截图的action名称 */
        r.put("snapscreenUrlPrefix", "/upload/");/* 图片访问路径前缀 */
        r.put("snapscreenInsertAlign", "none");/* 插入的图片浮动方式 */

        /* 抓取远程图片配置 */
        r.put("catcherLocalDomain", new String[]{"127.0.0.1", "localhost", "img.baidu.com"});
        r.put("catcherActionName", "catchimage");
        r.put("catcherFieldName", "source");
        r.put("catcherUrlPrefix", "/upload/");
        r.put("catcherMaxSize", "2048000");
        r.put("catcherAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"});

        /* 上传视频配置 */
        r.put("videoActionName", "uploadvideo");
        r.put("videoFieldName", "file");
        r.put("videoUrlPrefix", "/upload/");
        r.put("videoMaxSize", "1024000000");
        r.put("videoAllowFiles", new String[]{".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
                ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid"});
        /* 上传文件配置 */
        r.put("fileActionName", "uploadfile");
        r.put("fileFieldName", "file");
        r.put("fileMaxSize", "51200000");
        r.put("fileAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp",
                ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
                ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
                ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
                ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"});
        /* 列出指定目录下的图片 */
        r.put("imageManagerActionName", "listimage");
        r.put("imageManagerListPath", "/upload/");
        r.put("imageManagerListSize", "20");
        r.put("imageManagerUrlPrefix", "/upload/");
        r.put("imageManagerAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"});
        return r;
    }

    /**
     * 上传文件返回文件名称
     *
     * @param file
     * @return
     */
    @POST("/image")
    @FILE(max = 1024 * 1024 * 40)
    public Object SaveFile(UploadedFile file) {
        File fl = ImgKit.savebyfile(file);
        Record r = new Record();
//		String path=Proper.getProp("application.properties").get("upload.imgurl","/upload/");
        r.put("id", fl.getName());
        r.put("success", true);
        return r;
    }

    @POST("/file")
    @FILE
    public Object File(UploadedFile file) {
        File fl = ImgKit.savebyfile(file);
        return fl.getName();
    }

    /**
     * 根据文件名删除文件
     *
     * @return
     */
    @DELETE("/image")
    public boolean DeleteFile() {
        String name = getParam("name");
        String url = getRequest().getRealPath("upload");
//		System.out.println(url);
        File f = new File(url + "\\" + name);
        return f.delete();
    }

    /**
     * ckeditor控件 图片上传回调
     *
     * @param upload
     * @throws IOException
     */
    @POST("/getimage")
    @FILE
    public void GetFile(UploadedFile upload) throws IOException {
        File f = ImgKit.savebyfile(upload);
        String path = Proper.getProp("application.properties").get("upload.imgurl", "/upload/");
        String num = getParam("CKEditorFuncNum");
        PrintWriter out = this.getResponse().getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + num
                + ",'" + path + f.getName() + "','上传成功！'" + ")");
        out.println("</script>");
        out.flush();
        out.close();
    }
}
