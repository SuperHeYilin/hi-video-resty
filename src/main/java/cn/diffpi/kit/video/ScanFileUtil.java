package cn.diffpi.kit.video;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author superhe
 */
public class ScanFileUtil {

	/**
	 * 文件id
	 */
	private static int count  = 1;


	/**
	 * 文件集合
	 */
	private static List<String> fileList = new ArrayList<>();

	/**
	 * 文件夹集合
	 */
	private static List<Map<String, Object>> directoryList = new ArrayList<>();

	private static Map<String, Object> map = new HashMap<>();

	public static void main(String[] args) {

		Map<String, Object> testMap = listFiles("D:\\豪哥");
		List<Map<String, Object>> testList = (List<Map<String, Object>>) testMap.get("directories");

		for (Map<String, Object> stringObjectMap : testList) {
			System.out.print(stringObjectMap.get("id") + " ");
			System.out.print(stringObjectMap.get("fileName") + " ");
			System.out.print(stringObjectMap.get("path") + " ");
			System.out.println(stringObjectMap.get("fatherId"));
		}

	}

	/**
	 * 扫描文件
	 *
	 * @param path 绝对路径
	 * @return
	 */
	public static Map<String, Object> listFiles(String path) {
		int temp = count - 1;
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
					// 判断是否为文件夹
					if (file2.isDirectory()) {
						Map<String, Object> dirMap = new HashMap<>();
						dirMap.put("id", count++);
						dirMap.put("fatherId", temp);
						dirMap.put("fileName", file2.getName());
						dirMap.put("path", file2.getAbsolutePath());
						directoryList.add(dirMap);
						// 递归调用
						listFiles(file2.getAbsolutePath());
					} else {
						String fileName = file2.getName();
						if (fileName.endsWith("avi") || fileName.endsWith("mp4") || fileName.endsWith("wmv") || fileName.endsWith("FLV")) {
							System.out.print("文件夹:" + file2.getAbsolutePath());
							System.out.print("  文件名:" + file2.getName());
							System.out.print("  文件大小MB:" + file2.length() / 1024 / 1024);
							System.out.println("  文件大小b:" + file2.length());
							if (file2.length() / 1024 / 1024 < 10) {
								return null;
							}
							fileList.add(file2.getAbsolutePath());
						}
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
		map.put("files", fileList);
		map.put("directories", directoryList);
		return map;
	}

}
