package game;
/*
 *@author circle
 *@version 创建时间：2017年1月6日下午5:25:17
 */

import interface_lib.ExcelManage;
import interface_lib.Http;
import interface_lib.newHttp;
import interface_lib.Http;
import net.sf.json.JSONObject;

public class getTicketResult  {
	private static Object[][] testobject = { { 802600647,
		"f0e6cf50c5aaa2648633099574568c317e06572dea30aed2766860893496c7ec",
			1 }, };

	public static void main(String[] args) throws Exception {
		/* 加入响应时间统计 */
		String sheetname = "getresult";
		ExcelManage em = new ExcelManage();
		String title[] = { "start", "end", "responsetime" };
		em.createExcel("E:/test2.xls", sheetname, title);

		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/jiawawa/game/getUseTicketResult";

		// int t = testobject.length;// 读取测试环境用例数

		Object[] testcase;// for循环中存储测试用例

		// for循环读取并执行用例
		for (int i = 0; i < 3; i++) {
			testcase = testobject[0];

			// 生成接口请求参数的data部分
			long timestamp = System.currentTimeMillis();
			int ticketId=90;
//			String recordId = startTest.getTicketData(ticketId);
			String recordId ="928702791336214528";
			if (recordId.equals("null")) {

				System.out.println("recordId 为空");

				break;
			}
			JSONObject data = new JSONObject();
			data.put("kugouId", testcase[0]);
			data.put("token", testcase[1]);
			data.put("timestamp", timestamp);
			data.put("giftId", 54);
			data.put("recordId", recordId);
			data.put("channel", 1);
			data.put("appId", 1010);

			// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]

			// String[] JSON = newHttp.jwwPost(Host+URL, data);

			// 统计响应时间的
			String[] JSON = Http.jwwPost(Host + URL, data, em, sheetname);
			// 打印请求的JSON

			JSONObject jo03 = JSONObject.fromObject(JSON[1]);
			String jsonvalue04 = jo03.get("data").toString();
			//
			// if (jsonvalue04.equals("1"))
			// {
			// System.out.println("抽中了，返回的data=1");
			//
			// break;
			// }
			// 打印响应的JSON[没格式化的JSON]
			System.out.println("请求：" + JSON[0]);
			// 打印响应的JSON[格式化的JSON]
			System.out.print(i);
			System.out.println("响应：" + JSON[1]);
		}

	}
}
