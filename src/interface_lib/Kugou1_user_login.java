package interface_lib;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
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
public class Kugou1_user_login {

  @SuppressWarnings({ "rawtypes", "unchecked" })
public static  List gettoken(String Host,String username,String pwd) throws Exception {
	  
//	  String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQWlCJKux6th72ZLa9+axSvJqvZdmUNQt2ExGcqx41Xd6ToGICPPzDdSf+Vk/LqLop2xoTz4JpxSpO8YyBNTyS77b4/Jf7Iu8p38NCS33J133sC+03deUII1ClkCwseHjaBPGkTHUH2IJgGLd6dZCrsaSGP418a70kJTb5DvoLywIDAQAB";
	  
//	        System.err.println("公钥加密——私钥解密");
//	        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
//	        System.out.println("\r加密前文字：\r\n" + source);
//	        byte[] data3 = source.getBytes();
//	        byte[] encodedData = RSAUtils2.encryptByPublicKey(data3, publicKey);
//	        System.out.println("加密后文字：\r\n" + new String(encodedData));
////	        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
////	        String target = new String(decodedData);
////	        System.out.println("解密后文字: \r\n" + target);
	    

	  
	  
	  
	  
	  
	  
	  
	  
	  
	  String url = Host + "/api/user/login";
		
		

	//sign的规则是"caller+(clientTime=值)+ecret_key"
	  long clientTime = System.currentTimeMillis();//获取系统当前时间，以毫秒输出
	  String clientTime1 = Long.toString(clientTime);//把系统时间转换成字符串
//	  String pwd = Reg_MD5Encode.MD5encode("67889911");
//	  String pwd = "67889911";
	  
	  
	  
	  Map<String, Object> pwdMap = Maps.newHashMap();
		pwdMap.put("clienttime", clientTime1);
		pwdMap.put("pwd", DigestUtils.md5Hex(pwd));//pwd进行MD5加密
//		String jsonStr = JsonUtils.map2String(pwdMap);
		
		JSONObject jsonStr02 = JSONObject.fromObject(pwdMap); 

//		  System.out.println(jsonStr02);
		  LoginToken logintoken = new LoginToken();
		String p = logintoken.getRASEncrypt(jsonStr02.toString());

	  
//	  String pwd2=  "{\"clienttime\":" + "\"" + clientTime1 + "\"" + ",\"pwd\":" + "\"" + pwd + "\"" + "}";
//	  byte[] srtbyte = pwd2.getBytes();
//	  System.out.println(pwd2);
	  
//	  byte[] p = RSAUtils2.encryptByPublicKey(srtbyte,publicKey);

	  
//	  System.out.println(p+1);
//	  String res = new String(p);

//	  System.out.println(res);
	  
	  
//	  System.out.println(p);
	  JSONObject data = new JSONObject();
	  data.put("username", username);
	  data.put("p", p);
	  data.put("clientver", 100);
	  data.put("mid", "67F7B249-1D6D-44A6-BDED-D982E08C3595");
	  data.put("devicesn", "BND1eqsBQv3mgBLaHffi6MyMYwwxpz6s");
	  data.put("ip", "172.17.20.87");
    
	  
		
	  
    JSONObject params = new JSONObject();
    params.put("clientTime", clientTime);
    params.put("caller", "IOS");
    params.put("version", "100");
    params.put("data", data);
    
    String caller = params.get("caller").toString();
//    String clientTime = params.get("clientTime").toString();
    String ecret_kety= "9e615c8a150a21f44a0eac71d82e19f20c1a49ff";
    String sign = Reg_MD5Encode.MD5encode(caller + "clientTime=" + clientTime + ecret_kety).toLowerCase();
    
//    System.out.println(caller + "clientTime=" + clientTime + ecret_kety);
//    String sign = Reg_MD5Encode.MD5encode("ANDROIDclientTime=1407140613657b0914551195352024d099ef62c681fa36a2909e0");
    
//    System.out.println(sign);
    
    params.put("sign", sign);
//    JSONArray params2 = new JSONArray();
//    System.out.println(params);
    
//    JSONObject param3 = new JSONObject();
//    param3.put("DOC_TP_CODE", "10100");
//    param3.put("DOC_NBR", "100200198301202210");
//    param3.put("DOC_CUST_NM", "test");
    
    
//    params2.add(param3);
//    params.put("DOCS", params2);
    String ret = doPost(url, params).toString();
    JSONObject jo = JSONObject.fromObject(ret);
	String jsonvalue = jo.get("data").toString();
	
	JSONObject jo1 = JSONObject.fromObject(jsonvalue);
//	String token = jo.get("token").toString();
	
	
	String token = jo1.get("token").toString();
	String kgUid =jo1.get("kgUid").toString();				
		List list = new ArrayList<String>();
		list.add(token);
		list.add(kgUid);
		return list;
	
	
//	return token;
//    System.out.println(ret);
  }

  /**
   httpClient的get请求方式2
   * @return
   * @throws Exception
   */
//  public static String doGet(String url, String charset)
//      throws Exception {
//    /*
//     * 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 1:生成一个 HttpClinet 对象并设置相应的参数。
//     * 2:生成一个 GetMethod 对象并设置响应的参数。 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get
//     * 方法。 4:处理响应状态码。 5:若响应正常，处理 HTTP 响应内容。 6:释放连接。
//     */
//    /* 1 生成 HttpClinet 对象并设置参数 */
//    HttpClient httpClient = new HttpClient();
//    // 设置 Http 连接超时为5秒
//    httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//    /* 2 生成 GetMethod 对象并设置参数 */
//    GetMethod getMethod = new GetMethod(url);
//    // 设置 get 请求超时为 5 秒
//    getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
//    // 设置请求重试处理，用的是默认的重试处理：请求三次
//    getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,	new DefaultHttpMethodRetryHandler());
//    String response = "";
//    /* 3 执行 HTTP GET 请求 */
//    try {
//      int statusCode = httpClient.executeMethod(getMethod);
//      /* 4 判断访问的状态码 */
//      if (statusCode != HttpStatus.SC_OK) {
//        System.err.println("请求出错: "+ getMethod.getStatusLine());
//      }
//      /* 5 处理 HTTP 响应内容 */
//      // HTTP响应头部信息，这里简单打印
//      Header[] headers = getMethod.getResponseHeaders();
//      for (Header h : headers)
//        System.out.println(h.getName() + "------------ " + h.getValue());
//      // 读取 HTTP 响应内容，这里简单打印网页内容
//      byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
//      response = new String(responseBody, charset);
//      System.out.println("----------response:" + response);
//      // 读取为 InputStream，在网页内容数据量大时候推荐使用
//      // InputStream response = getMethod.getResponseBodyAsStream();
//    } catch (HttpException e) {
//      // 发生致命的异常，可能是协议不对或者返回的内容有问题
//      System.out.println("请检查输入的URL!");
//      e.printStackTrace();
//    } catch (IOException e) {
//      // 发生网络异常
//      System.out.println("发生网络异常!");
//      e.printStackTrace();
//    } finally {
//      /* 6 .释放连接 */
//      getMethod.releaseConnection();
//    }
//    return response;
//  }

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
//        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(res.getEntity());// 返回json格式：
        response = JSONObject.fromObject(result);
//      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return response;
  }



}