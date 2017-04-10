package game;

import interface_lib.Http;
import interface_lib.newHttp;
import net.sf.json.JSONObject;

//查询账户余额接口
/*
 *@author circle
 *@version 创建时间：2016年11月10日下午6:19:17
 */
public class getAccount {
	private static Object[][] testobject = { { 802600647,
			"f0e6cf50c5aaa2648633099574568c3147da951bb2ba6dc875ebbfa6f9dea802",
			1 }, };

	public static void main(String[] args) throws Exception {
		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/jiawawa/user/getAccounttMoney";

		// int t = testobject.length;// 读取测试环境用例数

		Object[] testcase;// for循环中存储测试用例

		// for循环读取并执行用例
		for (int i = 1; i < 2; i++) {
			testcase = testobject[0];

			// 生成接口请求参数的data部分
			long timestamp = System.currentTimeMillis();

			JSONObject data = new JSONObject();
			data.put("kugouId", testcase[0]);
			data.put("token", testcase[1]);
			data.put("timestamp", timestamp);
			data.put("appId", 1010);
			// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]

			String[] JSON = newHttp.jwwPost(Host + URL, data);
			JSONObject jo03 = JSONObject.fromObject(JSON[1]);
			String jsonvalue04 = jo03.get("data").toString();

			if (jsonvalue04.equals("0.0")) {
				System.out.print("获取到的余额为0");
				break;
			}
			// 打印请求的JSON
			System.out.println("请求：" + JSON[0]);
			// 打印响应的JSON[格式化的JSON]
			System.out.println("响应：" + JSON[1]);
			// 打印响应的JSON[没格式化的JSON]

		}

	}
}
