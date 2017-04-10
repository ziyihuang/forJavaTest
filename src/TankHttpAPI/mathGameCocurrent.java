package TankHttpAPI;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import interface_lib.Http;
import interface_lib.newHttp;
import net.sf.json.JSONObject;

/**
 * @author huangziyi@circle
 * @date 2017年3月29日 time 下午5:09:03
 * 
 */
public class mathGameCocurrent {
	
	private static Object[][] testobject = {
			{ 802600647, "f0e6cf50c5aaa2648633099574568c31abb92d767fa8e1d880bee29c65570c9", 1 }, };

	public static void main(String[] args) throws Exception {
		// 设置接口URL
		String Host = Http.gettesthost();
		String URL = "/tanke/game/matchingGame";

		int c = 100;
		CyclicBarrier cb = new CyclicBarrier(c);
		ExecutorService es = Executors.newFixedThreadPool(c);
		Object[] testcase;// for循环中存储测试用例
		
		// for循环读取并执行用例
		for (int i = 0; i < c; i++) {
			testcase = testobject[0];

			// 生成接口请求参数的data部分
			long timestamp = System.currentTimeMillis();
			JSONObject data = new JSONObject();
			int kugouId=(int) Math.ceil(Math.random()*10000000);
			int token=(int) Math.ceil(Math.random()*10000000);
			data.put("kugouId", kugouId);
			data.put("token", token);
			data.put("clientTime", timestamp);
			data.put("mapId",1);
			data.put("mountSkillId", 1);
			data.put("playLevel", 1);
			data.put("tankeId", 1);
			data.put("tankeSkillId", 1);
//		    Thread.sleep(300);
			// 执行用例，获取请求的JSON和响应的JSON[也可能是"服务器返回502错误"等]
			HttpPost httppost= newHttp.TankePost(Host + URL, data);
			//开启多线程
			es.execute(new MyThread(cb, httppost));
			
		}
		es.shutdown();
	}

}


class MyThread implements Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */

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
//			cb.await();
			// 定义线程要实现的业务
			HttpResponse hr = chc.execute(httppost);
//			Thread.sleep(100);
			// 拿到响应实体并打印[没格式化的JSON]
			String eu = EntityUtils.toString(hr.getEntity(), "utf-8");
			System.out.println(eu);
//		    System.out.println(JSONObject.fromObject(eu));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	
