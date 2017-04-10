package pangxiong;

import java.util.List;

import interface_lib.Assert;
import interface_lib.Http;
import interface_lib.Javaxmail;
import interface_lib.LoginToken;
import interface_lib.login3_Online;
import net.sf.json.JSONObject;

public class BuylogListTest_online {
	// 用例设计[设置data里的参数值，但不包含token]
	private static Object[][] object = { 
			{802600647, 1, 1, 20, 0, 0, "zytest002/test1234", "status" },// 获取kgcbet1243全部购买记录第一页20条
			
	};

	public static void main(String[] args) throws Exception {
		// 从配置文件中获取环境变量Host的值
		String Host = Http.getonlinehost();
		//String Host = Http.getonlinehost();
		// 设置接口URL
		String URL = "/api/user/buyLog/list";

		// 循环读取并执行用例
		for (int i = 0; i < object.length; i++) {
			// 读取用例
			Object[] testcase = object[i];
			String[] up = ( (String) testcase[testcase.length - 2]).split("/");
			// 获取用例所用帐号对应的token
			String token =login3_Online.getToken(up[0], up[1]);
			// 生成接口请求参数的data部分
			JSONObject data = new JSONObject();
			data.put("kgUid", testcase[0]);
			data.put("token", token);
			data.put("status", testcase[1]);// 0游客，1用户
			data.put("pageNumber", testcase[2]);
			data.put("pageSize", testcase[3]);
			data.put("type", testcase[4]);// 0全部，3进行中，5已揭晓
			data.put("targetKgUid", testcase[5]);// 不为0则是他人的

			// 执行用例，获取请求的JSON和响应的JSON
			String[] JSON = Http.doPost(Host + URL, data);
			// 打印请求的JSON
			System.out.println(JSON[0]);
			// 打印响应的JSON[格式化的JSON]
			System.out.println(JSON[1]);
			// 打印响应的JSON[没格式化的JSON]
//			 System.out.println(JSONObject.fromObject(JSON[1]));

			// 调用对应的断言方法，拿到结果pass或fail
//			testcase[testcase.length - 1] = Assert.judge(JSON[1], testcase[testcase.length - 1]);
			// 调用对应的断言方法，拿到结果pass或fail
			String result = Assert.judge(JSON[1], testcase[testcase.length - 1]);
			if (!result.equals("pass")) {
				Javaxmail.mail(result, Host + URL, JSON);
			}
		}

		// 打印输出用例执行结果
//		for (int i = 0; i < object.length; i++) {
//			System.err.print("用例" + (i + 1) + "：");
//			System.err.println(object[i][object[i].length - 1]);
//		}
		
	}
}