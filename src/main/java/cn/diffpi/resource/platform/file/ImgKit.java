package cn.diffpi.resource.platform.file;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

//import com.drew.imaging.jpeg.JpegMetadataReader;
//import com.drew.imaging.jpeg.JpegProcessingException;
//import com.drew.metadata.Directory;
//import com.drew.metadata.Metadata;
//import com.drew.metadata.MetadataException;
//import com.drew.metadata.exif.ExifDirectory;

import cn.diffpi.resource.platform.file.model.PtMedia;
import cn.dreampie.common.http.UploadedFile;


public class ImgKit {
    public static String readBaiDuConfig() {
        try {
            Reader reader = new FileReader("D:\\diffpi\\.metadata\\.me_tcat7\\webapps\\admin\\WEB-INF\\classes\\config.json");
            int ch = reader.read();
            StringBuffer buffer = new StringBuffer();
            while (ch != -1) { //读取成功
                buffer.append((char) ch);
                ch = reader.read();
            }
//	         System.out.println(buffer.toString());
            //3、关闭流
            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param uploadFile
     * @return
     */
    public static void editFileNameAndRotate(UploadedFile uploadFile) {
        File file = uploadFile.getFile();
        String fileName = uploadFile.getOriginalFileName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String path = file.getPath().replace(file.getName(), "");
        path = path.replace("\\", "\\\\");
        Date now = new Date();
        String diskName = getRandomName(now, suffix);
        String realPath = path + File.separator + diskName;
        File newFile = new File(realPath);
        boolean res = file.renameTo(newFile);
        if (res) {
            res = ImgKit.rotatePhonePhoto(newFile, getRotateAngleForPhoto(newFile));
        }
//		return null;
    }


    public static File savecopyFile(File oldfile, String path) {
        try {
            int byteread = 0;
            String fileName = oldfile.getName();
            File filepath = new File(path);
            if (!filepath.exists() && !filepath.isDirectory()) {
                filepath.mkdir();//如果文件夹不存在则创建
            }
            String realPath = path + File.separator + getRandomName(new Date(), fileName.substring(fileName.lastIndexOf(".")).toLowerCase());
            InputStream inStream = new FileInputStream(oldfile); //读入原文件
            File newFile = new File(realPath);
            FileOutputStream fs = new FileOutputStream(newFile);
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
            return newFile;
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param uploadFile
     * @return
     */
    public static File savebyfile(UploadedFile uploadFile) {
        PtMedia pm = new PtMedia();
        String upName = uploadFile.getFileName();
        String prefix = upName.substring(upName.lastIndexOf("."));
        pm.set("original_name", upName)
                .set("size", uploadFile.getFile().length())
                .set("mime_type", prefix)
                .set("local_path", "/upload/")
                .set("del", 0);
        File file = uploadFile.getFile();
        String fileName = uploadFile.getOriginalFileName();
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        String path = file.getPath().replace(file.getName(), "");
        path = path.replace("\\", "\\\\");
        Date now = new Date();
        String diskName = getRandomName(now, suffix.toLowerCase());
        String realPath = path + File.separator + diskName;
        File newFile = new File(realPath);
        boolean res = file.renameTo(newFile);
        if (res) {
//			res = ImgKit.rotatePhonePhoto(newFile, getRotateAngleForPhoto(newFile));
            pm.set("file_name", newFile.getName()).save();
            return newFile;
        }
        return null;
    }

    /**
     * 获取随机文件名
     *
     * @return
     */
    public static String getRandomName(Date d, String suffix) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return d.getTime() + "" + uuid + suffix;
    }

    /**
     * 获取图片正确显示需要旋转的角度（顺时针）
     *
     * @return
     */
    public static int getRotateAngleForPhoto(File file) {
//        int angle = 0;
//        Metadata metadata;
//        try {
//            metadata = JpegMetadataReader.readMetadata(file);
//            Directory directory = metadata.getDirectory(ExifDirectory.class);
//            if (directory.containsTag(ExifDirectory.TAG_ORIENTATION)) {
//                // Exif信息中方向　　
//                int orientation = directory.getInt(ExifDirectory.TAG_ORIENTATION);
//                // 原图片的方向信息
//                if (6 == orientation) {
//                    //6旋转90
//                    angle = 90;
//                } else if (3 == orientation) {
//                    //3旋转180
//                    angle = 180;
//                } else if (8 == orientation) {
//                    //8旋转90
//                    angle = 270;
//                }
//            }
//        } catch (JpegProcessingException e) {
//            e.printStackTrace();
//            return angle;
//        } catch (MetadataException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return angle;
        return 0;
    }

    /**
     * 旋转手机照片
     *
     * @return
     */
    public static Boolean rotatePhonePhoto(File file, int angel) {
        BufferedImage src;
        try {
            src = ImageIO.read(file);
            int src_width = src.getWidth(null);
            int src_height = src.getHeight(null);
            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);
            BufferedImage res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = res.createGraphics();
            g2.translate((rect_des.width - src_width) / 2,
                    (rect_des.height - src_height) / 2);
            g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
            g2.drawImage(src, null, null);
            ImageIO.write(res, "jpg", file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }
}
