package game;

import interface_lib.Http;
import interface_lib.newHttp;
import net.sf.json.JSONObject;

/*
 *@author circle
 *@version 创建时间：2016年11月17日上午11:57:19
 */
public class setAdress {
	private static Object[][] testobject = { 
		{802600647,"f0e6cf50c5aaa2648633099574568c31c475f8e5000bb324ebfddde193a76e94",  1 },
};


public static void main(String[] args) throws Exception {
	// 设置接口URL
	String Host = Http.gettesthost();
	String URL = "/api/user/setGiftAddress";

	int t = testobject.length;// 读取测试环境用例数

	Object[] testcase;// for循环中存储测试用例

	// for循环读取并执行用例
	for (int i = 0; i < t ; i++) {
			testcase = testobject[0];

		// 生成接口请求参数的data部分
	   long timestamp = System.currentTimeMillis();
		String recordId = startTest.getData();
		if(recordId.equals("null"))
		{
			
			System.out.println("recordId 为空");
		
		  	break;		
		}
		JSONObject data = new JSONObject();
		data.put("kugouId", testcase[0]);
		data.put("token", testcase[1]);
		data.put("timestamp", timestamp);
		data.put("id","913078587303804928");
		data.put("receiver", "xiaoceshi");
		data.put("mobile", "1555465545454");
		data.put("address", "测试一下填别人收货地址");
        data.put("appId",1010);
		// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]

			
		String[] JSON = newHttp.jwwPost(Host+URL, data);
		// 打印请求的JSON

		
		JSONObject jo03 = JSONObject.fromObject(JSON[1]);
		String jsonvalue04 = jo03.get("data").toString();
//		
	
		// 打印响应的JSON[没格式化的JSON]
		System.out.println("请求："+JSON[0]);
		// 打印响应的JSON[格式化的JSON]
		System.out.print(i);
		System.out.println("响应："+JSON[1]);
		}

	}
}

