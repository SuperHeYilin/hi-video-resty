package cn.diffpi.core.common;

import cn.dreampie.common.util.properties.Prop;
import cn.dreampie.common.util.properties.Proper;

public final class MyConstant {
	public final static String newsImgBasePath;//新闻图片文件主路径
	public final static String newsImgSubPath;//新闻图片文件子路径

	static {
		Prop constants = null;
		try {
			constants = Proper.use("application.properties");
		} catch (Exception e) {
		}

		if(constants == null){
			newsImgBasePath = "";
			newsImgSubPath = "";
		} else {
			newsImgBasePath = constants.get("cus.upload.newsImgBasePath");
			newsImgSubPath = constants.get("cus.upload.newsImgSubPath");
		}
	}
}
