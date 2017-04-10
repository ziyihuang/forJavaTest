package game;

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

/*
 *@author circle
 *@version 创建时间：2016年11月14日下午5:17:45
 */
public class getResultCurrent1 {
	private static Object[][] testobject = { 
		{802600647,"f0e6cf50c5aaa2648633099574568c31c475f8e5000bb324ebfddde193a76e94",  1 },
		{824290205,"80e67193cfdcc48a3009d8cb1780b88f9dd43a8fb59479476444af767598c452",  1 },
};


public static void main(String[] args) throws Exception {
	// 设置接口URL
	String URL = "http://172.17.13.137:9000/api/game/getResult";
	// 并发量c为用例数
//	int t = testobject.length;
	int c = 2;
	CyclicBarrier cb = new CyclicBarrier(c);
	ExecutorService es = Executors.newFixedThreadPool(c);


	Object[] testcase;// for循环中存储测试用例
//	Object[] testcase1;

	// for循环读取并执行用例

	for (int i = 0; i < 2 ; i++) {
		if(i<1){
			testcase = testobject[0];}
		else{
			testcase = testobject[1];}
			

		// 生成接口请求参数的data部分
	   long timestamp = System.currentTimeMillis();
       Object kugouid=testcase[0];
       Object Token=testcase[1];
//      String recordId = "122334";
      String recordId = startTest.getnewData(kugouid, Token);
	
		JSONObject data = new JSONObject();
		data.put("kugouId", kugouid);
		data.put("token", Token);
		data.put("timestamp", timestamp);
		data.put("giftId", 6);
		data.put("recordId", recordId);

		HttpPost httppost = Http.jwwcurrentPost(URL, data);
//		System.out.println(data);

		// 开启多线程

		es.execute(new MyThread(cb, httppost));
	}
//	for(int j = 0; j < 5 ; j++)
//	{
//
//		testcase = testobject[1];
//
//	// 生成接口请求参数的data部分
//   long timestamp = System.currentTimeMillis();
//   Object kugouid=testcase[0];
//   Object Token=testcase[1];
//  String recordId = startTest.getnewData(kugouid, Token);
//
//	JSONObject data = new JSONObject();
//	data.put("kugouId", kugouid);
//	data.put("token", Token);
//	data.put("timestamp", timestamp);
//	data.put("giftId", 6);
//	data.put("recordId", recordId);
//
//	HttpPost httppost = Http.jwwcurrentPost(URL, data);
//
//	es.execute(new MyThread(cb, httppost));
//
//	}
	es.shutdown();
		
		}
//	
	
public static class MyThread implements Runnable {
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
			System.out.println(eu);
		    //System.out.println(JSONObject.fromObject(eu));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	

}



