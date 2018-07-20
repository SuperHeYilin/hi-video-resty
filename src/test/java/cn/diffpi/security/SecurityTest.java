package cn.diffpi.security;

import java.util.HashMap;
import java.util.Map;

import cn.diffpi.kit.HttpCon;
import cn.diffpi.security.aes.AesTool;
import cn.diffpi.security.aes.Signature;
import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.client.ClientResult;

public class SecurityTest {
	public static void main(String[] args) throws Exception {
		AesTool aes = new AesTool();
		Signature signatureUtil = new Signature();

		String appid = "cbt_kuaigou_appid";
		String token = "cbt_kuaigou_token";
		String key = aes.findKeyById(appid);
		long millis = 1531281175L;//System.currentTimeMillis();
		System.out.println(millis);
		String data = "123";
		String xml = aes.encrypt(data, key);

		String lol = signatureUtil.digest(data, "MD5");
		String signature = signatureUtil.generateSignature(appid, token, lol, millis);

		System.out.println(xml);
		System.out.println(signature);

		Map<String, String> paraMap = new HashMap<String, String>();

		paraMap.put("sign", signature);
		paraMap.put("appid", appid);
		paraMap.put("timestamp", String.valueOf(millis));
		paraMap.put("lol", lol);

		Client client = new Client("http://localhost:8080/api/b/v1.0/pt/goods/receive"+HttpCon.buildQuery(paraMap, "utf-8", "GET"));

		ClientRequest cr = new ClientRequest();
		cr.setJsonParam(xml);

		ClientResult clientResult = client.build(cr).post();
		System.out.println(clientResult.getResult());
	}
}
