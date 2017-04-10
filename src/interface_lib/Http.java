package interface_lib;

import java.io.IOException;
import java.io.InputStream;

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

public class Http {
	@SuppressWarnings("rawtypes")
	public String get(String URL, Map map) throws IOException {
		String smap = map.toString().replace("{", "").replace(", ", "&")
				.replace("}", "");
		// get
		HttpGet get = new HttpGet(URL + "?" + smap);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);

		HttpEntity entity = response.getEntity();
		String sentity = EntityUtils.toString(entity, "utf-8");

		// InputStream input = response.getEntity().getContent();
		// String sentity = IOUtils.toString(input);
		return sentity;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	// application/x-www-form-urlencoded的post方法
	public String post(String URL, Map map) throws IOException {

		List<NameValuePair> list = Lists.newArrayList();
		for (Iterator<Entry<String, Object>> iterator = map.entrySet()
				.iterator(); iterator.hasNext();) {
			Entry<String, Object> entry = iterator.next();
			list.add(new BasicNameValuePair(entry.getKey(), String
					.valueOf(entry.getValue())));
		}
		// post
		HttpPost post = new HttpPost(URL);
		post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(post);

		HttpEntity entity = response.getEntity();
		String sentity = EntityUtils.toString(entity, "utf-8");

		// InputStream input = response.getEntity().getContent();
		// String sentity = IOUtils.toString(input);
		return sentity;
	}

	public static String gettesthost() throws IOException {
		Properties pro = new Properties();
		InputStream input = Http.class.getResourceAsStream("Config.properties");
		pro.load(input);
		input.close();
		String Host = pro.getProperty("testHost");
		return Host;
	}

	public static String getonlinehost() throws IOException {
		Properties pro = new Properties();
		InputStream input = Http.class.getResourceAsStream("Config.properties");
		pro.load(input);
		input.close();
		String Host = pro.getProperty("onlineHost");
		return Host;
	}

	@SuppressWarnings("rawtypes")
	public static String gettoken(String Host, Object[] testcase)
			throws Exception {
		// 获取用例中所用的帐号密码
		String[] up = ((String) testcase[testcase.length - 2]).split("/");
		// 去大酷狗拿token
		List list = LoginToken.getToken(up[0], up[1]);
		String token = "从大酷狗login_by_pwd获取token失败";
		String kgUid1 = "112233";
		try {
			token = list.get(0).toString();
			kgUid1 = list.get(1).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// long kgUid = Long.parseLong(kgUid1);
		// //到login2登录一次，使token生效
		// try {
		// Kugou1_user_login2.getToken(Host,token,kgUid,up[0],up[1]);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return token;
	}

	// @SuppressWarnings("rawtypes")
	public static String login2gettoken(Object[] testcase) throws Exception {
		// 获取用例中所用的帐号密码
		String[] up = ((String) testcase[testcase.length - 2]).split("/");

		// 去大酷狗拿token
		List list = LoginToken.getToken(up[0], up[1]);
		String token = "从大酷狗login_by_pwd获取token失败";
		try {
			token = list.get(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	@SuppressWarnings("rawtypes")
	public static String getp(Object[] testcase) throws Exception {
		// 获取用例中所用的帐号密码
		String[] up = ((String) testcase[testcase.length - 2]).split("/");

		// 得出String类型的当前时间戳
		long clientTime = System.currentTimeMillis();
		String clientTime1 = Long.toString(clientTime);

		Map<String, Object> pwdMap = Maps.newHashMap();
		pwdMap.put("clienttime", clientTime1);
		pwdMap.put("pwd", DigestUtils.md5Hex(up[1]));// pwd进行MD5加密

		JSONObject jsonStr02 = JSONObject.fromObject(pwdMap);

		LoginToken logintoken = new LoginToken();
		String p = logintoken.dsgetRASEncrypt(jsonStr02.toString());

		return p;
	}

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

	public static HttpPost post(String url, JSONObject data)
			throws UnsupportedEncodingException {
		// 传接口的外部公共数据 这个方法只是先准备好请求数据，但未向服务器发送请求。用于测试并发，并发需要等待后才集中发请求

		long clientTime = System.currentTimeMillis();

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
		/*
		 * HttpEntity实体是一个接口，实现这个接口的具体类有很多，
		 * 比较常用的是StringEntity、UrlEncodedFormEntity
		 * （继承自StringEntity）、MultipartEntity
		 */
		// 生成post
		HttpPost post = new HttpPost(url);
		StringEntity s = new StringEntity(params.toString());
		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");
		post.setEntity(s);

		return post;

	}

	public static String[] jwwPost(String url, JSONObject data, ExcelManage em,String sheetname) {
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
			long a = System.currentTimeMillis();// 请求时间
			HttpResponse res = client.execute(post);
			long r = System.currentTimeMillis();// 响应时间戳
			long t = r - a;

			// 写入到excel
			User user1 = new User();
			user1.setStart(a);
			user1.setEnd(r);
			user1.setResponsetime(t);
			em.writeToExcel("E:/test2.xls", sheetname, user1);

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

	/* 夹娃娃并发 */
	public static HttpPost jwwcurrentPost(String url, JSONObject data)
			throws UnsupportedEncodingException {
		CloseableHttpClient client = HttpClients.createDefault();

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