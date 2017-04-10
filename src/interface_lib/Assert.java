package interface_lib;

import net.sf.json.JSONObject;

public class Assert {
	public static String judge(String json, Object testcase) {
		String result = "没有匹配的断言方法";
		if (testcase == "status") {
			result = status(json, testcase);
		}
		if (testcase == "status_data_kgUid_username") {
			result = status_data_kgUid_username(json, testcase);
		}
		return result;
	}

	public static String status(String json, Object testcase) {
		// 获取响应JSON中status值[json也可能是"服务器返回502错误"等]
		int status = 0;
		try {
			JSONObject jo = JSONObject.fromObject(json);
			status = jo.getInt("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (status == 1)
			return "pass";
		else
			return "reason：返回的json中status不等于1";
	}

	public static String status_data_kgUid_username(String json, Object testcase) {
		// 获取响应JSON中status值[json也可能是"服务器返回502错误"等]
		int status = 0;
		long kgUid = 0;
		String username = "";
		try {
			JSONObject jo = JSONObject.fromObject(json);
			status = jo.getInt("status");
			JSONObject data = jo.getJSONObject("data");
			kgUid = data.getLong("kgUid");
			username = data.getString("username");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (status == 1 && kgUid > 0 && username.length() > 0)
			return "pass";
		else
			return "reason：返回的json中status不等于1，或data中kgUid不大于0，或data中username长度不大于0";
	}
}