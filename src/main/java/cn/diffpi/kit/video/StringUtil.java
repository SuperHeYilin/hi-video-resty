package cn.diffpi.kit.video;

import java.util.HashSet;
import java.util.Set;

/**
 * @author super
 * @date 2018/4/8 16:12
 */
public class StringUtil {
	public static void main(String[] args) {

		String s = "heyzo_hd_0603_full.wmv02_尚学堂_高明鑫websocket_打开和New Java_STRU1CH01_Session1_Part01.FLV关闭通道 发送消息(1).avi";

		for (String string : split(s)) {
			System.out.print(string + "   ");
		}
	}


	/**
	 * 提取字符串中的关键字
	 * @param string
	 * @return
	 */
	public static Set<String> split(String string) {
		Set<String> set = new HashSet<String>();

		//heyzo    hd    0603    full    wmv02    尚学堂    高明鑫websocket    打开和关闭通道    发送消息(1)    avi
		String[] strings = string.split("\\.| |-|_|、|\\(|\\)");

		String[] str = {};
		for (String s : strings) {
			if ("".equals(s)) {
				continue;
			}
			for (String sNum : s.split("\\D")) {
				if ("".equals(s)) {
					continue;
				}
				set.add(sNum);
			}
			for (String sNum : s.split("\\w")) {
				if ("".equals(s)) {
					continue;
				}
				set.add(sNum);
			}
			for (String sNum : s.split("\\d")) {
				if ("".equals(s)) {
					continue;
				}
				set.add(sNum);
			}
			for (String sWord : s.split("[\\u4e00-\\u9fa5]")) {
				if ("".equals(s)) {
					continue;
				}
				set.add(sWord);
			}
			set.add(s);
		}
		System.out.println(set.size());
		return set;
	}


}
