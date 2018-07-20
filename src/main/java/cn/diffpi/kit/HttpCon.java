package cn.diffpi.kit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.mysql.jdbc.StringUtils;

/***
 * 网络工具类
 * 
 * @author lx
 */
public class HttpCon {

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";
	private static final int RED_TIME_OUT = 30000;
	private static final int Connect_TIME_OUT = 5000;

	private static HttpsURLConnection getHttpsUrlCon(String requestUrl) throws IOException {
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			return httpUrlConn;
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static HttpURLConnection getHttpUrlCon(String requestUrl) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			return httpUrlConn;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static HttpURLConnection getUrlCon(String requestUrl, String ctype, Map<String, String> headerMap, String method, boolean isHttps) throws IOException {
		HttpURLConnection httpUrlConn = null;
		try {
			if (isHttps) {
				httpUrlConn = getHttpsUrlCon(requestUrl);
			} else {
				httpUrlConn = getHttpUrlCon(requestUrl);
			}

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// httpUrlConn.setConnectTimeout(Connect_TIME_OUT);
			// httpUrlConn.setReadTimeout(RED_TIME_OUT);

			if (ctype != null) {
				// ctype = "text/html";
				httpUrlConn.setRequestProperty("content-type", ctype);
			}

			if (headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httpUrlConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(method);
			return httpUrlConn;
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * 请求
	 * 
	 * @param requestUrl
	 * @param outputStr
	 * @param ctype
	 * @param headerMap
	 * @param isHttps
	 * @param method
	 * @return
	 */
	private static String doRequst(String requestUrl, String outputStr, String ctype, Map<String, String> headerMap, boolean isHttps, String method) {
		String respStr = null;
		try {
			HttpURLConnection httpUrlConn = getUrlCon(requestUrl, ctype, headerMap, method, isHttps);

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes(DEFAULT_CHARSET));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			respStr = respJsonStr(inputStream);
			httpUrlConn.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return respStr;
	}
	
	/***
	 * 请求
	 * 
	 * @param requestUrl
	 * @param outputStr
	 * @param ctype
	 * @param headerMap
	 * @param isHttps
	 * @param method
	 * @return
	 */
	public static HttpURLConnection doPostHttpURLConn(String requestUrl, String outputStr, String ctype, Map<String, String> headerMap, boolean isHttps) {
		try {
			HttpURLConnection httpUrlConn = getUrlCon(requestUrl, ctype, headerMap, "POST", isHttps);

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes(DEFAULT_CHARSET));
				outputStream.close();
			}

			return httpUrlConn;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * 请求
	 * 
	 * @param requestUrl
	 * @param outputStr
	 * @param ctype
	 * @param headerMap
	 * @param isHttps
	 * @param method
	 * @return
	 */
	public static HttpURLConnection doGetHttpURLConn(String requestUrl, String outputStr, String ctype, Map<String, String> headerMap, boolean isHttps) {
		try {
			HttpURLConnection httpUrlConn = getUrlCon(requestUrl, ctype, headerMap, "GET", isHttps);

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes(DEFAULT_CHARSET));
				outputStream.close();
			}

			return httpUrlConn;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * web请求(默认为application/x-www-form-urlencoded表单格式)
	 * 
	 * @param requestUrl 请求路径
	 * @param outputStr 要提交的数据
	 * @param ctype content-type(数据形式)
	 * @param headerMap setRequestProperty
	 * @param isHttps 是否为https请求
	 * @return
	 */
	public static String doPost(String requestUrl, String outputStr, String ctype, Map<String, String> headerMap, boolean isHttps) {
		return doRequst(requestUrl, outputStr, ctype, headerMap, isHttps, METHOD_POST);
	}

	/***
	 * web请求(默认为application/x-www-form-urlencoded表单格式)
	 * 
	 * @param requestUrl 请求路径
	 * @param propertys 要提交的参数
	 * @param ctype content-type(数据形式)
	 * @param headerMap setRequestProperty
	 * @param isHttps 是否为https请求
	 * @return
	 */
	public static String doPost(String requestUrl, Map<String, String> propertys, String ctype, Map<String, String> headerMap, boolean isHttps) {
		try {
			String params = buildQuery(propertys, DEFAULT_CHARSET, METHOD_POST);
			return doPost(requestUrl, params, ctype, headerMap, isHttps);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/***
	 * (multipart/form-data)表单格式支持流跟参数同时传递 web请求
	 * 
	 * @param requestUrl 请求路径
	 * @param propertys 要提交的参数
	 * @param outputStr 提交的流数据字符串(需要根据情况重写)
	 * @param headerMap setRequestProperty
	 * @param isHttps 是否为https请求
	 * @return
	 */

	private static String doPostMul(String requestUrl, Map<String, String> propertys, String outputStr , Map<String, String> headerMap, boolean isHttps) { 
		if (headerMap == null) {
			headerMap = new HashMap<String, String>();
		}
		String boundary = "-----------------------------" + System.currentTimeMillis();
		headerMap.put("Connection", "Keep-Alive");
		// headerMap.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
		headerMap.put("Content-type", "multipart/form-data; boundary=" + boundary);// 设置流跟表单的请求方式
		headerMap.put("Charset", "UTF-8");
	 	
		String propertyStr = buildQueryByRequest(propertys, boundary);
		
		return null;
	}

	/***
	 * (multipart/form-data)上传文件并带参数
	 * 
	 * @param requestUrl 请求路径
	 * @param propertys 要提交的参数
	 * @param outFiles 提交的文件列表(属性名 , 文件)
	 * @param headerMap headerMap
	 * @param isHttps 是否为https请求
	 * @return
	 */
	public static String doPostFile(String requestUrl, Map<String, String> propertys, Map<String, List<File>> outFiles, Map<String, String> headerMap, boolean isHttps) {
		if (headerMap == null) {
			headerMap = new HashMap<String, String>();
		}
		String boundary = "-----------------------------" + System.currentTimeMillis();
		headerMap.put("Connection", "Keep-Alive");
		// headerMap.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
		headerMap.put("Content-type", "multipart/form-data; boundary=" + boundary);// 设置流跟表单的请求方式
		headerMap.put("Charset", "UTF-8");

		String respStr = null;
		HttpURLConnection httpUrlConn;
		try {
			httpUrlConn = getUrlCon(requestUrl, null, headerMap, METHOD_POST, isHttps);

			OutputStream outputStream = httpUrlConn.getOutputStream();

			String outputStr = buildQueryByRequest(propertys, boundary);

			// 当有数据需要提交时
			if (outputStr != null && !outputStr.equals("")) {
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes(DEFAULT_CHARSET));
			}
			if (outFiles != null && outFiles.size() > 0) {
				buildQueryByFile(outFiles, boundary, outputStream);
			}

			outputStream.write(("--" + boundary + "--\r\n\r\n").getBytes(DEFAULT_CHARSET));
			outputStream.close();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			respStr = respJsonStr(inputStream);
			httpUrlConn.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respStr;
	}

	/***
	 * GET请求带输出流
	 * 
	 * @param requestUrl 请求路径
	 * @param outputStr 要提交的数据(以流的形式)
	 * @param ctype content-type
	 * @param headerMap setRequestProperty
	 * @param isHttps 是否为https请求
	 * @return
	 */
	public static String doGet(String requestUrl, String outputStr, String ctype, Map<String, String> headerMap, boolean isHttps) {
		return doRequst(requestUrl, outputStr, ctype, headerMap, isHttps, METHOD_POST);
	}

	/***
	 * GET方式带参数和者流数据的请求(url不能带?跟参数)
	 * 
	 * @param requestUrl 请求路径
	 * @param propertys 要提交的参数
	 * @param ctype
	 * @param outputStr 提交的流数据字符串
	 * @param content-type
	 * @param headerMap
	 * @param setRequestProperty
	 * @param isHttps 是否为https请求
	 * @return
	 */
	public static String doGet(String requestUrl, Map<String, String> propertys, String outputStr, String ctype, Map<String, String> headerMap, boolean isHttps) {
		try {
			String params = buildQuery(propertys, DEFAULT_CHARSET, METHOD_GET);
			requestUrl = requestUrl + (params == null ? "" : params);
			return doRequst(requestUrl, outputStr, ctype, headerMap, isHttps, METHOD_GET);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String respJsonStr(InputStream inputStream) {
		try {
			StringBuffer buffer = new StringBuffer();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;

			return buffer.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * 通过request头将要传递的参数手动设置到表单信息
	 * 
	 * @param params
	 * @param boundary
	 * @return
	 */
	private static String buildQueryByRequest(Map<String, String> params, final String boundary) {
		if (params == null) {
			return null;
		}

		Set<Entry<String, String>> entries = params.entrySet();
		StringBuffer strBuf = new StringBuffer();
		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (!StringUtils.isNullOrEmpty(name)) {
				strBuf.append("--" + boundary + "\r\n");
				strBuf.append("Content-Disposition: form-data; name=\"" + name + "\"\r\n\r\n");
				strBuf.append(value + "\r\n");
			}
		}

		return strBuf.toString();
	}

	/***
	 * 通过request头将要传递的文件设置到文本中
	 * 
	 * @param params
	 * @param setRequestProperty
	 * @param boundary
	 * @return
	 * @throws Exception
	 */
	private static void buildQueryByFile(Map<String, List<File>> outfiles, final String boundary, final OutputStream out) throws Exception {
		if (outfiles == null) {
			return;
		}
		Set<Entry<String, List<File>>> entries = outfiles.entrySet();

		for (Entry<String, List<File>> entry : entries) {
			List<File> files = entry.getValue();

			String inputName = entry.getKey();

			for (File file : files) {
				String fileName = URLEncoder.encode(file.getName(), DEFAULT_CHARSET);

				if (inputName == null || file == null) {
					continue;
				}

				String contentType = getContentType(file);

				// MagicMatch方法现存在问题用getContentType方法代替
				/*
				 * MagicMatch match = null; try { match = Magic.getMagicMatch(file, false, true); contentType = match.getMimeType(); } catch (MagicParseException e) { e.printStackTrace(); } catch (MagicMatchNotFoundException e) { e.printStackTrace(); } catch (MagicException e) { e.printStackTrace(); }
				 */

				StringBuffer head = new StringBuffer();
				head.append("--").append(boundary).append("\r\n");
				head.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + fileName + "\"\r\n");
				head.append("Content-Type:" + contentType + "\r\n\r\n");

				out.write(head.toString().getBytes(DEFAULT_CHARSET));

				byte[] fileData = getFileBytes(file);

				out.write(fileData);

				out.write("\r\n".getBytes(DEFAULT_CHARSET));
			}
		}
	}

	// 把文件转换成字节数组
	private static byte[] getFileBytes(File f) throws IOException {
		FileInputStream in = new FileInputStream(f);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int n;
		while ((n = in.read(b)) != -1) {
			out.write(b, 0, n);
		}
		in.close();
		out.close();
		return out.toByteArray();
	}

	// 获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream
	private static String getContentType(File f) throws Exception {

		// return "application/octet-stream"; // 此行不再细分是否为图片，全部作为application/octet-stream 类型
		ImageInputStream imagein = ImageIO.createImageInputStream(f);
		if (imagein == null) {
			return "application/octet-stream";
		}
		Iterator<ImageReader> it = ImageIO.getImageReaders(imagein);
		if (!it.hasNext()) {
			imagein.close();
			return "application/octet-stream";
		}
		imagein.close();
		return "image/" + it.next().getFormatName().toLowerCase();// 将FormatName返回的值转换成小写，默认为大写
	}

	public static String buildQuery(Map<String, String> params, String charset, String method) throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (!StringUtils.isNullOrEmpty(name) && !StringUtils.isNullOrEmpty(value)) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=").append(URLEncoder.encode(value, charset));
			}
		}

		if (query.length() != 0 && method.equals(METHOD_GET)) {
			query.insert(0, "?");
		}
		return query.toString();
	}
}
