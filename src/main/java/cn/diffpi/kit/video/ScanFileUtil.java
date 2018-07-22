package cn.diffpi.kit.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author superhe
 */
public class ScanFileUtil {

	private static List<String> list = new ArrayList<String>();

	public static void main(String[] args) {

//		System.out.println(listFiles("D:\\迅雷下载").size());
		for (String list: listFiles("D:\\迅雷下载")){
			System.out.println(list);
		}

	}

	/**
	 * 扫描文件
	 * @param path
	 * @return
	 */
	private static List<String> listFiles(String path) {

		File file = new File(path);

		if (file.exists()) {
			File[] files = file.listFiles();
			if (files == null) {
				return null;
			}
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
			} else {
				for (File file2 : files) {
					// 判断是否问文件夹
					if (file2.isDirectory()) {
//						System.out.println("文件夹:" + file2.getAbsolutePath());
						listFiles(file2.getAbsolutePath());
					} else {
//            System.out.println("文件:" + file2.getAbsolutePath());
						String fileName = file2.getName();
						if (fileName.endsWith("avi") || fileName.endsWith("mp4") || fileName.endsWith("wmv") || fileName.endsWith("FLV")) {
							System.out.print("文件夹:" + file2.getAbsolutePath());
							System.out.print("  文件名:" + file2.getName());
							System.out.print("  文件大小MB:" + file2.length() / 1024 / 1024);
							System.out.println("  文件大小b:" + file2.length());
							if (file2.length() / 1024 / 1024 < 10) {
								return null;
							}
							list.add(file2.getAbsolutePath());
						}
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
		return list;
	}

}
