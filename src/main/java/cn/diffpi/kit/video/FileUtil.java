package cn.diffpi.kit.video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 *
 * @author super
 */
public class FileUtil {
	public static void main(String[] args) {

		deleteFile("F:\\Studio\\Workspace\\workspace_idea\\Shit\\target\\SSMTest-1.0-SNAPSHOT\\images\\1522987520737.jpg");

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
