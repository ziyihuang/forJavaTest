package interface_lib;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.mail.internet.InternetAddress;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
/*
 *@author circle
 *@version 创建时间：2016年10月17日下午5:18:16
 */
public class newHttp {

	//json 格式请求
	public static String[] doPost(String url, JSONObject data) {

		// 传接口的外部公共数据 这个方法准备好请求数据并向服务器发送请求
		long clientTime = System.currentTimeMillis();
		// 传接口的外部公共数据
		JSONObject params = new JSONObject();
		params.put("clientTime", clientTime);
		params.put("caller", "ANDROID");
		params.put("version", "1.2.0");
		params.put("data", data);

		String caller = params.get("caller").toString();
		String ecret_kety = "b0914551195352024d099ef62c681fa36a2909e0";
		String sign = Reg_MD5Encode.MD5encode(
				caller + "clientTime=" + clientTime + ecret_kety).toLowerCase();

		params.put("sign", sign);

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost post = new HttpPost(url);
		// System.out.println(params);
		String[] Object = new String[2];
		Object[0] = params.toString();
		try {
			StringEntity s = new StringEntity(params.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);

			HttpResponse res = client.execute(post);
			// 获取响应的result,并转字符串
			int intresult = res.getStatusLine().getStatusCode();
			String sintresult = Integer.toString(intresult);

			if (intresult != 200) {
				Object[1] = "服务器返回" + sintresult + "错误";
			} else {
				String result = EntityUtils.toString(res.getEntity(), "utf-8");
				Object[1] = result;
			}
			return Object;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object[1] = "网络异常";
		return Object;

	}
	 
	public static HttpPost post(String url,JSONObject data) throws UnsupportedEncodingException{
		//传接口的外部公共数据  这个方法只是先准备好请求数据，但未向服务器发送请求。用于测试并发，并发需要等待后才集中发请求
		 
		 long clientTime = System.currentTimeMillis();
		 
		 JSONObject params = new JSONObject();
		    params.put("clientTime", clientTime);
		    params.put("caller", "ANDROID");
		    params.put("version", "1.2.0");
		    params.put("data", data);
		    
		    String caller = params.get("caller").toString();
		    String ecret_kety= "b0914551195352024d099ef62c681fa36a2909e0";
		    String sign = Reg_MD5Encode.MD5encode(caller + "clientTime=" + clientTime + ecret_kety).toLowerCase();
		    
		    params.put("sign", sign);
		    /*
		     * HttpEntity实体是一个接口，实现这个接口的具体类有很多，
		     * 比较常用的是StringEntity、UrlEncodedFormEntity（继承自StringEntity）、MultipartEntity
		     * */
		    //生成post
		    HttpPost post = new HttpPost(url);
		    StringEntity s = new StringEntity(params.toString());
		      s.setContentEncoding("UTF-8");
		      s.setContentType("application/json");
		      post.setEntity(s);
		      
			return post;
	}
	
	public static String[] jwwPost(String url, JSONObject data) {




		
		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost post = new HttpPost(url);
		// System.out.println(params);
		String[] Object = new String[2];
		Object[0] = data.toString();
		try {
			StringEntity s = new StringEntity(data.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);

			HttpResponse res = client.execute(post);
			// 获取响应的result,并转字符串
			int intresult = res.getStatusLine().getStatusCode();
			String sintresult = Integer.toString(intresult);

			if (intresult != 200) {
				Object[1] = "服务器返回" + sintresult + "错误";
			} else {
				String result = EntityUtils.toString(res.getEntity(), "utf-8");
				Object[1] = result;
			}
			return Object;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object[1] = "网络异常";
		return Object;

	}
	
	public static HttpPost TankePost(String url, JSONObject data)
			throws UnsupportedEncodingException, Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		Thread.sleep(100);
		HttpPost post = new HttpPost(url);

		String[] Object = new String[2];
		Object[0] = data.toString();
		// System.out.print("请求:"+Object[0]);

		StringEntity s = new StringEntity(data.toString());
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");
		post.setEntity(s);

		return post;
	}
}
