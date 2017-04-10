
package pangxiong;
import interface_lib.Javaxmail;
import interface_lib.Assert;
import interface_lib.Http;
import net.sf.json.JSONObject;

public class MyExchangeTest {
	// testobject存放测试环境运行的用例
	private static Object[][] testobject = { { 1500000015, "c342c1b6-1886-4e61-b94d-cb72a9848c3c", "15669714804/67889911", "status" },// 新用户[去酷狗化之后注册的用户]查询
			{ 601462539, "26f84fc3-4236-4967-8887-ab944de99839", "kgcbet1648/67889911", "status" },// 老用户[去酷狗化之前注册的用户]查询
	};
	// onlineobject存放线上环境运行的用例
	private static Object[][] onlineobject = {};

	public static void main(String[] args) throws Exception {
		// 从配置文件中获取测试环境和线上环境Host
		String testHost = Http.gettesthost();
		String onlineHost = Http.getonlinehost();
		// 设置接口URL
		String URL = "/api/user/myExchange";

		int t = testobject.length;// 读取测试环境用例数
		int o = onlineobject.length;// 读取线上环境用例数
		Object[] testcase;// for循环中存储测试用例
		String Host;// for循环中存储要请求的Host
		// for循环读取并执行用例
		for (int i = 0; i < t + o; i++) {
			// 依次读取用例并取对应的Host
			if (i < t) {
				Host = testHost;
				testcase = testobject[i];
			} else {
				Host = onlineHost;
				testcase = onlineobject[i - t];
			}
//			String token = Http.login3gettoken(testcase);// 获取用例所用帐号对应的token[去酷狗化之后通过login3获取token]
			// 生成接口请求参数的data部分
			JSONObject data = new JSONObject();
			data.put("kgUid", testcase[0]);
			data.put("token", testcase[1]);

			// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
			String[] JSON = Http.doPost(Host + URL, data);
			// 打印请求的JSON
			 System.out.println(JSON[0]);
			// 打印响应的JSON[格式化的JSON]
			 System.out.println(JSON[1]);
			// 打印响应的JSON[没格式化的JSON]
			try {
				System.out.println(JSONObject.fromObject(JSON[1]));
			} catch (Exception e) {
				// 有可能不是JSON格式的，则直接输出[如"服务器返回502错误"]
				System.out.println(JSON[1]);
			}

			// 调用对应的断言方法，拿到结果pass或fail
			String result = Assert.judge(JSON[1], testcase[testcase.length - 1]);
			if (!result.equals("pass")) {
				Javaxmail.mail(result, Host + URL, JSON);
			}
		}
	}
}