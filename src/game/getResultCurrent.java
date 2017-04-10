package game;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import interface_lib.Http;
import net.sf.json.JSONObject;

/*
 *@author circle
 *@version 创建时间：2016年11月14日下午5:17:45
 */
public class getResultCurrent {
	private static Object[][] testobject = {
			{
					802600647,
					"f0e6cf50c5aaa2648633099574568c3147da951bb2ba6dc875ebbfa6f9dea802",
					1 },
			{
					824290205,
					"80e67193cfdcc48a3009d8cb1780b88f9dd43a8fb59479476444af767598c452",
					1 }, };

	public static void main(String[] args) throws Exception {
		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/jiawawa/game/getResult";
		// 并发量c为用例数
		// int t = testobject.length;
		int c = 2;
		CyclicBarrier cb = new CyclicBarrier(c);
		ExecutorService es = Executors.newFixedThreadPool(c);

		Object[] testcase;// for循环中存储测试用例
		// Object[] testcase1;

		// for循环读取并执行用例

		for (int i = 0; i < 2; i++) {
			if (i < 1) {
				testcase = testobject[0];
			} else {
				testcase = testobject[1];
			}

			// 生成接口请求参数的data部分
			long timestamp = System.currentTimeMillis();
			Object kugouid = testcase[0];
			Object Token = testcase[1];
			String recordId = startTest.getnewData(kugouid, Token);
			// String recordId = "12345";
			JSONObject data = new JSONObject();
			data.put("kugouId", kugouid);
			data.put("token", Token);
			data.put("timestamp", timestamp);
			data.put("giftId", 53);
			data.put("recordId", recordId);
			data.put("channel", 1);
			data.put("appId", 1010);
			HttpPost httppost = Http.jwwcurrentPost(Host + URL, data);
			System.out.print(i);
			System.out.println(data);

			// 开启多线程

			es.execute(new MyThread(cb, httppost));
		}

		es.shutdown();

	}

	//
	static int a = 0;

	static class MyThread implements Runnable {
		private CyclicBarrier cb;
		private HttpPost httppost;

		public MyThread(CyclicBarrier cb, HttpPost httppost) {
			this.cb = cb;
			this.httppost = httppost;
		}

		@Override
		public void run() {
			try {
				CloseableHttpClient chc = HttpClients.createDefault();
				// 等待所有任务准备就绪
				cb.await();
				// 定义线程要实现的业务
				HttpResponse hr = chc.execute(httppost);

				// 拿到响应实体并打印[没格式化的JSON]
				String eu = EntityUtils.toString(hr.getEntity(), "utf-8");

				System.out.print(a++);

				System.out.println(eu);
				// System.out.println(JSONObject.fromObject(eu));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
