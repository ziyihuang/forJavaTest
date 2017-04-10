package pangxiong;

import interface_lib.Http;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//参数相同时，不同接口的并发
public class Temp1428 {
	// 用例设计[设置data里的参数值，但不包含token]
	private static Object[][] object = {
			{ "0CB20059A5A3765FD83472C74AAC0108EB043AEA46639D644AB91D36B555FFE695C3E125B215971FBFB8414AC3F4EF01C04685D218F9F2A4E0E7AA1BE75734AFFC534B4F4717CF4A9D7237201EFD1F2E8DE5B4C2463004A43A2B0D64EC561B918183F7EDE81018D27C81285E503D1E00E22CB905E51009109397C539712AA1AE", 1200572155, "100115816", "1", "http://1.kmh.kugou.com/api/user/win/cardSecret?_=1473058512100" },
			{ "0C0AF95468F0C79AD61310007B95ABBE6CF877703D6E110A5F1C0D154F81EDD8D80615AC9A5B7BF347833AE871510EA5529E113E63DEB66F3B8635FC3B1A46E3E5FE65B1B45F6031CAF3EC4648262EF128736F084E80964A55B8F97F6C0B93454F2E126518C7CFE10BFF60BC343D90ED1ECC41946BC0018DB784A0B8BCE727B2", 1200572155, "100115816", "1", "http://1.kmh.kugou.com/api/user/win/exchangeGiveMoney?_=1473058512100" },
	};

	public static void main(String[] args) throws Exception {
		// 并发量c为用例数
		int c = object.length;
		CyclicBarrier cb = new CyclicBarrier(c);
		ExecutorService es = Executors.newFixedThreadPool(c);

		
		

		// 循环读取并执行用例
		for (int i = 0; i < c; i++) {
			
			
			
			// 读取用例
			Object[] testcase = object[i];
			String URL = (String) testcase[4];

			 String token =
			 "0c94bf1b-9423-43fd-942c-49f71fc4e438";
			// 生成接口请求参数的data部分
			JSONObject data = new JSONObject();
			data.put("dataString", testcase[0]);
			data.put("kgUid", testcase[1]);
			data.put("robId", testcase[2]);
			data.put("status", testcase[3]);
			data.put("token", token);

			// 将接口地址和data数据带入post方法拿到HttpPost
			HttpPost httppost = Http.post( URL, data);

			// 开启多线程
			es.execute(new MyThread(cb, httppost));
		}
		es.shutdown();
	}

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
				System.out.println(JSONObject.fromObject(eu));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}