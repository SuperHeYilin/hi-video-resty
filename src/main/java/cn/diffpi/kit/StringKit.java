package cn.diffpi.kit;

import java.util.List;
import java.util.Random;

public class StringKit{
	public static String getListStr(List<String> ls,String flag){
		String str = "";
		for (String string : ls) {
			String newStr = string == null ? "" : string;
			str += newStr+flag;
		}
		return str.substring(0,str.length()-1);
	}
	public static String getStr(String... strings){
		String str = "";
		for (String string : strings) {
			String newStr = string == null ? "" : string;
			str += newStr;
		}
		return str;
	}
	
	public static String joinStr(Character flag , String... strings){
		if(strings == null ||  strings.length == 0){
			return "";
		}
		int offset = strings.length - 1;
		String newFlag = "";
		if(flag != null){
			newFlag = flag.toString();
		}
		StringBuffer str = new StringBuffer();
		for( int i = 0; i < offset; i++ ){
			String newStr = strings[i] == null ? "" : strings[i];
			
			str.append(newStr).append(newFlag);
		}
		
		str.append(strings[offset]);
		return str.toString();
	}

	/***
	 * 得到标记字符串
	 * 
	 * @param str
	 * @param startIndex开始索引
	 * @param length长度
	 * @param flag标记符
	 * @return xx***x
	 */
	public static String getFlagStr(String str, int startIndex, int length, Character flag) {
		if (!isNull(str)) {
			String newStr = "";

			if (flag == null || (flag + "").equals("")) {
				flag = '*';
			}
			try {
				char[] c = str.toCharArray();
				for (int i = 0; i < c.length; i++) {
					if (i >= startIndex && i < startIndex + length) {
						newStr += flag;
					} else {
						newStr += c[i];
					}
				}
			} catch (Exception e) {
				return str;
			}
			return newStr;
		}

		return str;
	}
	
	/***
	 * 获取时间搓
	 * @return
	 */
	public static Long currentTimeMillis(){
		return System.currentTimeMillis();
	}
	
	public static boolean isNull(String str){
		return str == null || str.trim().length() == 0;
	}
	
	public static boolean isNotBlank(String str){
		return str != null && str.trim().length() >= 1;
	}
	
	public static boolean equals(String str1 , String str2){
		if(str1 != null){
			return str1.equals(str2);
		} else if(str2 == null){
			return true;
		} else {
			return false;
		}
	}
	
	/***
	 * 截取从匹配的索引以后的字符串
	 * @param originalStr
	 * @param startStr
	 * @return
	 */
	public static String subEndStr(String originalStr , String startStr){
		if(isNotBlank(originalStr) && startStr != null){
			return originalStr.substring(originalStr.indexOf(startStr)+startStr.length()+1);
		} else {
			return null;
		}
	}
	
	/***
	 * 获取几位随机数字
	 * @param charCount
	 * @return
	 */
	public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
	
	/***
	 * 获取1位随机数
	 * @param from
	 * @param to
	 * @return
	 */
	 public static int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
	 }
}
