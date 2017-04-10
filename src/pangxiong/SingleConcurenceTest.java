package pangxiong;
import interface_lib.Http;
import interface_lib.login3;
import interface_lib.newHttp;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
public class SingleConcurenceTest {

	// 用例设计[设置data里的参数值，但不包含token]
	private static Object[][] object = {
//			{ 802600647, 1, 1, 20, 0, 0, "kgcbet1243/67889911", "status" }
			{801299319, 1, 1, 20, 0, 0, "zytest006/test1234", "status" },// 获取kgcbet1243全部购买记录第一页20条
			//{ 664167103, 1, 2, 20, 0, 0, "kgcbet1243/67889911", "status" },// 获取kgcbet1243全部购买记录第二页20条
	};
	
	
	public static void main(String[] args)throws Exception{
		
		int c=100;//并发量 为c
		CyclicBarrier cb= new CyclicBarrier(c); //CyclicBarrier 是一个计数器
		ExecutorService es=Executors.newFixedThreadPool(c);
		
		// 从配置文件中获取环境变量Host的值
		String Host = Http.gettesthost();
		// 设置接口URL
		String URL = "/api/user/buyLog/list";
		// 循环读取并执行用例
				for (int i = 0; i < 1; i++) {
					// 读取用例
					Object[] testcase = object[0];
					// 获取用例所用帐号对应的token
					String token = login3.getToken("zytest006", "test1234");
					// 使用固定token[同帐号用不同token并发，只会有一个通过校验，这时需要用固定的token]
//					 String token =
//					 "8a1fbc38545d486a3c51b554aacfcc3364     c82afc61cbf454802cf5c8c2e14ebd";
					// 生成接口请求参数的data部分
					JSONObject data = new JSONObject();
					data.put("kgUid", testcase[0]);
					data.put("token", token);
					data.put("status", testcase[1]);
					data.put("pageNumber", testcase[2]);
					data.put("pageSize", testcase[3]);
					data.put("type", testcase[4]);
					data.put("targetKgUid", testcase[5]);

					// 将接口地址和data数据带入post方法拿到HttpPost
					//for(int a = 0; a < 100; a++)
					//{
					HttpPost httppost = Http.post(Host + URL, data);
//				    HttpPost httppost=newHttp.post(Host + URL, data);	
		           //开启多线程
					for(int a = 0; a < 2; a++){
					es.execute(new MyThreads(cb,httppost));
					}
	}
				es.shutdown();
	}
	
	
	static class MyThreads implements Runnable{
       private CyclicBarrier cb;
       private HttpPost httppost;
       //
      public MyThreads(CyclicBarrier cb,HttpPost httppost) {
    	   this.cb=cb;
    	   this.httppost=httppost;
		// TODO Auto-generated constructor stub
	}
		@Override
		public void run() {
			try{ 
			CloseableHttpClient ch=HttpClients.createDefault();	
			//等待所有任务准备就绪
			cb.await();
			//定义线程要实现的业务
			HttpResponse hr=ch.execute(httppost);
			//拿到响应实体并打印【没格式化的json】
			String eu=EntityUtils.toString(hr.getEntity(),"utf-8");
//			System.out.println("asdf");
			 System.out.println(JSONObject.fromObject(eu));
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		}
	}
	
