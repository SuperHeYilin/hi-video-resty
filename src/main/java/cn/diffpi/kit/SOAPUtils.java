package cn.diffpi.kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class SOAPUtils {

	private static String getSoapRequest(String xmlPath,
			Map<String, String> params) {
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					new File(xmlPath)), "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
			String soap = "";
			String tmp;
			while ((tmp = reader.readLine()) != null) {
				soap += tmp;
			}
			reader.close();
			isr.close();

			for (String paramName : params.keySet()) {
				String paramValue = params.get(paramName);
				soap = soap.replace(":" + paramName, paramValue);
			}

			return soap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param urlString
	 *            ：webservice的url
	 * @param SOAPAction
	 *            ：调用的接口中的方法名
	 * @param xmlPath
	 *            ：传入参数的xml文件路径
	 * @param params
	 *            ：需要传入的动态配置的参数
	 * @return：返回一个可指定编码的IO
	 * @throws Exception
	 */
	public static InputStreamReader getSoapInputStream(String urlString,
			String SOAPAction, String xmlPath, Map<String, String> params)
			throws Exception {
		try {
			String soap = getSoapRequest(xmlPath, params);
			if (soap == null) {
				return null;
			}
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Length", Integer.toString(soap
					.length()));
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction", urlString + "/" + SOAPAction);

			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			osw.write(soap);
			osw.flush();
			osw.close();

			InputStream is = conn.getInputStream();
			// 指定返回编码
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");

			return isr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}