package interface_lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.collect.Maps;

import net.sf.json.JSONObject;

public class login3  {


	public static String getToken(String username,String pwd) throws IOException{
		// 从配置文件中获取环境变量Host的值
	    String Host = Http.gettesthost();
		
		// 设置接口URL
		String URL = "/api/user/login3";

		  
		 /*  获取p值*/
		 long clientTime = System.currentTimeMillis();
		  String clientTime1 = Long.toString(clientTime);
		  
		  Map<String, Object> pwdMap = Maps.newHashMap();
			pwdMap.put("clienttime", clientTime1);
			pwdMap.put("pwd", DigestUtils.md5Hex(pwd));//pwd进行MD5加密
		
			JSONObject jsonStr02 = JSONObject.fromObject(pwdMap); 
			LoginToken Logintoken = new LoginToken();
			String p = LoginToken.dsgetRASEncrypt(jsonStr02.toString());
			
			// 生成接口请求参数的data部分
			JSONObject data = new JSONObject();
			data.put("username", username);
			data.put("p", p);
			data.put("clientver", 22);
			data.put("mid", "5de5ad815341c5c3ffd2d2230039e3a5");
			data.put("devicesn", "ggmBsXMNp0I1oEe06CcWDn2DwX5ar78t");
			data.put("source", "yiyuanmai");
    		data.put("ip", "172.17.32.50");
			
			// 执行用例，获取请求的JSON和响应的JSON
			String[] JSON = Http.doPost(Host + URL, data);
//    		String[] JSON = newHttp.doPost(Host + URL, data);
			
			JSONObject jo03 = JSONObject.fromObject(JSON[1]);
			String jsonvalue04 = jo03.get("data").toString();
			
//			System.out.print(jsonvalue04);
			
			JSONObject jo04 = JSONObject.fromObject(jsonvalue04);
			String token = jo04.get("token").toString();
//			System.out.print(token);
			return token ;
	}
	
	
}