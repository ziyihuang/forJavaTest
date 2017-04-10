package TankHttpAPI;

import com.sdicons.json.validator.impl.predicates.Str;

import interface_lib.Http;
import interface_lib.newHttp;
import net.sf.json.JSONObject;

/**
 * @author huangziyi@circle
 * @date 2017年3月28日 time 下午2:43:53
 * 
 */

public class matchingGameTest {
	private static Object[][] testobject = {
			{ 802600643, "f0e6cf50c5aaa2648633099574568c31abb92d767fa8e1d880bee29c65570c9", 1 }, };

	public static void main(String[] args) throws Exception {
		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/tanke/game/matchingGame";
		int t = testobject.length;// 读取测试环境用例数

		Object[] testcase;// for循环中存储测试用例

		// for循环读取并执行用例
		for (int i = 0; i < 1; i++) {
			testcase = testobject[0];

			// 生成接口请求参数的data部分
			long timestamp = System.currentTimeMillis();
			JSONObject data = new JSONObject();
			int kugouId=(int) Math.ceil(Math.random()*10000000);
			data.put("kugouId", kugouId);
			data.put("token", testcase[1]);
			data.put("clientTime", timestamp);
			data.put("mapId",1);
			data.put("mountSkillId", 1);
			data.put("playLevel", 3);
			data.put("tankeId", 1);
			data.put("tankeSkillId", 1);
//		    Thread.sleep(300);
			// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
			String[] JSON = newHttp.jwwPost(Host + URL, data);
			//

			// 打印请求的JSON
//			System.out.println(JSON[0]);
			// 打印响应的JSON[格式化的JSON]
//			JSON[1] = JSON[1].replace(",","\n");
			System.out.println(JSON[1]);
			JSONObject jo03 = JSONObject.fromObject(JSON[1]);
			Object jsonvalue04 = jo03.get("data").toString();
			JSONObject jo05=JSONObject.fromObject(jsonvalue04);
			Object roomid=jo05.get("virtualRoomId");
	
			System.out.println("房间id:---"+roomid+"-------"+"请求次数"+i);
			// 打印响应的JSON[没格式化的JSON]
			// try {
			// System.out.println(JSONObject.fromObject(JSON[1]));
			// } catch (Exception e) {
			// 有可能不是JSON格式的，则直接输出[如"服务器返回502错误"]
			// System.out.println(JSON[1]);
			// }

		}
	}
}
