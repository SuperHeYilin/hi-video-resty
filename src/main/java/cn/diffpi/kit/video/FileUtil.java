package cn.diffpi.kit.video;

import cn.diffpi.kit.StrKit;
import cn.dreampie.route.core.multipart.FILE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 *
 * @author super
 */
public class FileUtil {
	public static void main(String[] args) {

//		deleteFile("F:\\Studio\\Workspace\\workspace_idea\\Shit\\target\\SSMTest-1.0-SNAPSHOT\\images\\1522987520737.jpg");
//		openFile("D:\\下载\\BaiduNetdiskDownload\\webSocket-8视频\\视频");
//		newFolder("D:\\test");
		moveFile("D:\\下载\\test.txt", "F:\\");
//		System.out.println(findParent("D:\\下载\\test.txt"));
	}

	/**
	 * 返回某个文件或者文件夹的父级文件夹
	 * @param path
	 * @return
	 */
	public static String findParent(String path) {
		File file = new File(path);
		return file.getParent();
	}

	/**
	 * 打开某个文件或者文件夹
	 *
	 * @param path 文件路径
	 */
	public static void openFile(String path) {
		List<String> commands = new ArrayList<String>();
		commands.add("cmd");
		commands.add("/c");
		commands.add("start");
		commands.add(" ");
		commands.add(path);

		ProcessBuilder builder = BuilderUtil.getInstance();

		builder.command(commands);
		try {
			builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件夹
	 * @param filePath
	 */
	public static boolean newFolder(String filePath)  {
		boolean flag = false;
		try  {
			File  myFilePath  =  new File(filePath);
			if  (!myFilePath.exists())  {
				flag = myFilePath.mkdir();
			}
		}
		catch  (Exception  e)  {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 移动文件到指定目录
	 * @param oldPath 原始目录
	 * @param newPath	目标目录
	 */
	public static boolean moveFile(String oldPath, String newPath){
		boolean temp = false;
		try {
			File oldFile = new File(oldPath);
			String name = oldFile.getName();
			File newFile = new File(newPath + File.separator + name);
			File path = new File(newPath);
			//判断文件夹是否创建，没有创建则创建新文件夹
			if(!path.exists()){
				if (path.mkdirs()) {
					temp = oldFile.renameTo(newFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	/**
	 * 删除文件
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);

		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除成功");
				return true;
			} else {
				System.out.println("删除失败");
				return false;
			}
		} else {
			System.out.println("文件不存在");
			return false;
		}
	}

}
