package interface_lib;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Maps;
//import com.kugou.lib.LoginToken;
//import com.kugou.lib.MD5Util;

/**
 * 
 */
public class Kugou1_user_login2 {

	public static  void getToken(String Host,String token,long kgUid,String username,String pwd) throws Exception {
	  
	  String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQWlCJKux6th72ZLa9+axSvJqvZdmUNQt2ExGcqx41Xd6ToGICPPzDdSf+Vk/LqLop2xoTz4JpxSpO8YyBNTyS77b4/Jf7Iu8p38NCS33J133sC+03deUII1ClkCwseHjaBPGkTHUH2IJgGLd6dZCrsaSGP418a70kJTb5DvoLywIDAQAB";	  
	  String url = Host + "/user/login2";


	  long clientTime = System.currentTimeMillis();
	  String clientTime1 = Long.toString(clientTime);
	  
	  Map<String, Object> pwdMap = Maps.newHashMap();
		pwdMap.put("clienttime", clientTime1);
		pwdMap.put("pwd", DigestUtils.md5Hex(pwd));//pwd进行MD5加密
		
		JSONObject jsonStr02 = JSONObject.fromObject(pwdMap); 

//		  System.out.println(jsonStr02);
		  LoginToken logintoken = new LoginToken();
		String p = logintoken.dsgetRASEncrypt(jsonStr02.toString());
		
//		List list = Kugou1_user_login.gettoken("http://1.kmh.kugou.com/api",username,pwd);
		
//		String kgUid1 = list.get(1).toString();
//		System.out.println(token);
//		long kgUid = Long.parseLong(kgUid1); 
	  
	  JSONObject data = new JSONObject();
	  data.put("username", username);
	  data.put("kgUid", kgUid);//
	  data.put("token", token);
	  data.put("p", p);
	  data.put("clientver", 100);
	  data.put("mid", "67F7B249-1D6D-44A6-BDED-D982E08C3595");
	  data.put("devicesn", "pty5Y1kjXeM03CuW45B2PdF1w3em16RO");
	  data.put("ip", "172.17.20.87");
    
    JSONObject params = new JSONObject();
    params.put("clientTime", clientTime);
    params.put("caller", "IOS");
    params.put("version", "100");
    params.put("data", data);
    
    String caller = params.get("caller").toString();
    String ecret_kety= "9e615c8a150a21f44a0eac71d82e19f20c1a49ff";
    String sign = Reg_MD5Encode.MD5encode(caller + "clientTime=" + clientTime + ecret_kety).toLowerCase();
    
    params.put("sign", sign);
    String ret = doPost(url, params).toString();
//    System.out.println(ret);
  }



  /**
   * post请求
   * @param url
   * @param json
   * @return
   */
  
  public static JSONObject doPost(String url,JSONObject json){
	  CloseableHttpClient client =  HttpClients.createDefault();
//	  CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpPost post = new HttpPost(url);
    JSONObject response = null;
    try {
      StringEntity s = new StringEntity(json.toString());
      s.setContentEncoding("UTF-8");
      s.setContentType("application/json");//发送json数据需要设置contentType
      post.setEntity(s);
      HttpResponse res = client.execute(post);
      
//      if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//    	  System.out.println(chr.getStatusLine().getStatusCode());
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
//        System.out.println(result);
        response = JSONObject.fromObject(result);
//      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return response;
  }



}