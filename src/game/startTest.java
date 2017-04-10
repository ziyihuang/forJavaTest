package game;

import interface_lib.Http;
import interface_lib.newHttp;
import net.sf.json.JSONObject;

public class startTest {

	public static String getData() throws Exception {
		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/jiawawa/game/start";

		// 生成接口请求参数的data部分
		long timestamp = System.currentTimeMillis();
		JSONObject data = new JSONObject();
		data.put("kugouId", 802600647);
		data.put("token",
				"f0e6cf50c5aaa2648633099574568c317e06572dea30aed2766860893496c7ec");
		data.put("timestamp", timestamp);
		data.put("level", 1);
		data.put("appId", 1010);
		// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
		String[] JSON = newHttp.jwwPost(Host + URL, data);

		JSONObject jo03 = JSONObject.fromObject(JSON[1]);
		String jsonvalue04 = jo03.get("data").toString();

		return jsonvalue04;

	}

	public static String getTicketData(int ticketId) throws Exception {
		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/jiawawa/game/startUseTicket";

		// 生成接口请求参数的data部分
		long timestamp = System.currentTimeMillis();
		JSONObject data = new JSONObject();
		data.put("kugouId", 802600647);
		data.put("token",
				"f0e6cf50c5aaa2648633099574568c317e06572dea30aed2766860893496c7ec");
		data.put("timestamp", timestamp);
		data.put("level", 1);
		data.put("appId", 1010);
		data.put("ticketId", ticketId);
		
		// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
		String[] JSON = newHttp.jwwPost(Host + URL, data);

		JSONObject jo03 = JSONObject.fromObject(JSON[1]);
		String jsonvalue04 = jo03.get("data").toString();

		return jsonvalue04;

	}
	public static String getnewData(Object kugouid, Object token)
			throws Exception {
		String Host = Http.gettesthost();
		String URL = "/jiawawa/game/start";

		// 生成接口请求参数的data部分
		long timestamp = System.currentTimeMillis();
		JSONObject data = new JSONObject();
		data.put("kugouId", kugouid);
		data.put("token", token);
		data.put("timestamp", timestamp);
		data.put("level", 1);
		data.put("appId", 1010);
		// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
		String[] JSON = newHttp.jwwPost(Host + URL, data);
        
		JSONObject jo03 = JSONObject.fromObject(JSON[1]);
		String jsonvalue04 = jo03.get("data").toString();
		return jsonvalue04;
	}
}
