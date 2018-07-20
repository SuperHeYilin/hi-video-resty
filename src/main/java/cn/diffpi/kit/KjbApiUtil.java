package cn.diffpi.kit;

import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.common.util.properties.Prop;
import cn.dreampie.common.util.properties.Proper;
import cn.dreampie.log.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class KjbApiUtil {
    private static final Logger logger = Logger.getLogger(KjbApiUtil.class);
    private String url = "";
    private String key = "";
    private static Prop prop;
    static {
         prop = Proper.use("application.properties");
    }
	public KjbApiUtil(){
	    this.url = prop.get("kjbapi.url");
	    this.key = prop.get("kjbapi.key");
	}
	public KjbApiUtil(String url,String key){
	    this.url=url;
	    this.key=key;
    }
	public JSONArray Post(String fun,Map<String, ? extends Object> map){
	    if(map==null){
	        logger.error("map变量为空");
	    }
	    logger.debug("请求方法:%s",fun);
	    logger.debug("请求参数：%s",JSONObject.toJSON(map).toString());
        String datas = aesEncrypt(JSONObject.toJSON(map).toString(),key);
        ClientRequest cr = new ClientRequest();
        cr.addParam("data", datas);
        Client c = new Client(url+fun);
        c.build(cr);
        String rst = c.post().getResult();
        JSONObject o = JSONObject.parseObject(rst);
        String s = (String)o.get("data");
        if(s==null){
            logger.error("请求错误:%s",o);
            return null;
        }else{
            JSONArray ja = JSONArray.parseArray(aesDecrypt(s,key));
            return ja;
        }
	}
	public static String aesEncrypt(String message, String key) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"),"AES");
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            byte[] r = cipher.doFinal(message.getBytes("UTF-8"));
	            return Base64.encodeBase64String(r);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	 public static String aesDecrypt(String message, String key) {
	        try {
	            byte[] bytesrc = Base64.decodeBase64(message);
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"),"AES");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            byte[] retbyte = cipher.doFinal(bytesrc);
	            return new String(retbyte,"utf-8");
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
