package cn.diffpi.core.ranamer;

import java.io.File;

import cn.diffpi.core.common.MyConstant;
import cn.diffpi.kit.DateUtil;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.util.stream.FileRenamer;

/**
 * 新闻图片文件上传命名惯例
 * Created by one__l on 2016年5月14日
 */
public class NewsImgFilesRenamer extends FileRenamer {
    // is atomic and used here to mark when a file name is chosen
    public File rename(File f) {
        String baseDirectory = MyConstant.newsImgBasePath + "/" + MyConstant.newsImgSubPath;
        String dynDirectory = DateUtil.getCurrentDate("/yyyy/MM/");


        createNewDir(baseDirectory + dynDirectory);

        Long fileName = System.currentTimeMillis();

        String name = f.getName();
        String ext;
        int dot = name.lastIndexOf(".");
        if (dot != -1) {
            ext = name.substring(dot);  // includes "."
        } else {
            ext = "";
        }

        int count = 0;
        do {
            count++;
            String newName = fileName + count + ext;
            f = new File(baseDirectory + dynDirectory, newName);
        } while (!createNewFile(f) && count < 9999);

        return f;
    }

    public boolean createNewDir(String path) {
        File dir = new File(path);
        boolean dirbool = true;
        if (!dir.exists()) {
            dirbool = dir.mkdirs();

            if (!dirbool) {
                throw new HttpException("Directory " + path + " not exists and can not create directory.");
            }
        }

        return dirbool;
    }
}
