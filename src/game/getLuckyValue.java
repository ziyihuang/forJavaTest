package game;

import interface_lib.Http;
import interface_lib.newHttp;
import net.sf.json.JSONObject;

/*
 *@author circle
 *@version 创建时间：2016年11月11日上午10:27:06
 */

public class getLuckyValue  {
	private static Object[][] testobject = { 
		{802600647,"f0e6cf50c5aaa2648633099574568c31c475f8e5000bb324ebfddde193a76e94",  1 },
};


public static void main(String[] args) throws Exception {
	// 设置接口URL
	
	String URL = "http://172.17.13.137:9000/api/gameGift/getLuckyValue";

//	int t = testobject.length;// 读取测试环境用例数

	Object[] testcase;// for循环中存储测试用例

	// for循环读取并执行用例

			testcase = testobject[0];

		// 生成接口请求参数的data部分
	   long timestamp = System.currentTimeMillis();
		
		JSONObject data = new JSONObject();
		data.put("kugouId", testcase[0]);
		data.put("token", testcase[1]);
		data.put("timestamp", timestamp);
	// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
		
		String[] JSON = newHttp.jwwPost(URL, data);
		JSONObject jo03 = JSONObject.fromObject(JSON[1]);
		String jsonvalue04 = jo03.get("data").toString();
		
	
		// 打印请求的JSON
		System.out.println(JSON[0]);
		// 打印响应的JSON[格式化的JSON]
		System.out.println(JSON[1]);
		// 打印响应的JSON[没格式化的JSON]
        
		}

	}


