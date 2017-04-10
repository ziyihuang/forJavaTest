package game;

import interface_lib.ExcelManage;
import interface_lib.Http;
import interface_lib.User;
import interface_lib.newHttp;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import pangxiong.BuyLog_listConcurrent.MyThread;
import net.sf.json.JSONObject;

/*
 *@author circle
 *@version 创建时间：2016年11月14日下午4:08:15
 */
public class startCurrent {
	private static Object[][] testobject = {
		{
			802600647,
			"f0e6cf50c5aaa2648633099574568c3147da951bb2ba6dc875ebbfa6f9dea802",
			1 },
	{
			824290205,
			"80e67193cfdcc48a3009d8cb1780b88f9dd43a8fb59479476444af767598c452",
			1 }, };
	static ExcelManage em = new ExcelManage();
	public static void main(String[] args) throws Exception {
		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/jiawawa/game/start";
		
		
		String title[] = { "start", "end", "responsetime" };
		em.createExcel("E:/test2.xls", "start", title);
	
		// 并发量c为用例数
		// int c = testobject.length;
		int c = 10;
		
		CyclicBarrier cb = new CyclicBarrier(c);
		ExecutorService es = Executors.newFixedThreadPool(c);

		Object[] testcase;// for循环中存储测试用例
		// for循环读取并执行用例
		for (int i = 0; i < 10; i++) {
			if (i < 5) {
				testcase = testobject[0];
			} else {
				testcase = testobject[1];
			}
			// 生成接口请求参数的data部分
			long timestamp = System.currentTimeMillis();
			JSONObject data = new JSONObject();
			data.put("kugouId", testcase[0]);
			data.put("token", testcase[1]);
			data.put("timestamp", timestamp);
			data.put("level", 1);
			data.put("appId", 1010);
			// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
			// String[] JSON = newHttp.jwwPost(URL, data);

			HttpPost httppost = Http.jwwcurrentPost(Host + URL, data);
			System.out.print(i);
			System.out.println(data);
//			System.out.println(Host + URL);
			// 开启多线程
		    es.execute(new MyThread(cb, httppost));
		}
	
		es.shutdown();
        
	}
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
					long i=System.currentTimeMillis();
					// 定义线程要实现的业务
					HttpResponse hr = chc.execute(httppost);
                    long j=System.currentTimeMillis();
                    long k=j-i;
                    System.out.println("响应时间"+k);
                	// 写入到excel
            		User user1 = new User();
            		user1.setStart(i);
            		user1.setEnd(j);
            		user1.setResponsetime(k);
            		em.writeToExcel("E:/test2.xls", "start", user1);
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
	
