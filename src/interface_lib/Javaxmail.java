package interface_lib;

import interface_lib.Http;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Javaxmail {
	public static void mail(String result, String subject, String message[]) throws Exception {
		// 从配置文件中获取测试环境Host
		String testHost = Http.gettesthost();
		//String onlineHost = Http.getonlinehost();
		// 测试环境不发邮件，写txt
		if (subject.contains(testHost)) {
			PrintWriter pw = new PrintWriter(new FileWriter("D:\\电商产品\\test.txt", true));
			Date date = new Date();
			pw.println(date);
			pw.println("-----------------------------------------------------------");
			pw.println(subject);
			pw.println(result);
			pw.println("请求参数：");
			pw.println(message[0]);
			pw.println("响应：");
			pw.println(message[1]);
			pw.println("-----------------------------------------------------------\r\n");
			pw.close();
		} else {
			// 配置发送邮件的环境属性
			final Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");// 需要身份验证
			properties.put("mail.smtp.host", "smtp.163.com");
//			properties.put("mail.user", "easetest007@163.com");
//			properties.put("mail.password", "test1234");
			properties.put("mail.user", "ziyi080723@163.com");
			properties.put("mail.password", "zy080723");

			// 构建授权信息，用于进行SMTP身份验证
			Authenticator authenticator = new Authenticator() {
				// getPasswordAuthentication方法返回PasswordAuthentication
				public PasswordAuthentication getPasswordAuthentication() {
					String username = properties.getProperty("mail.user");
					String password = properties.getProperty("mail.password");
					return new PasswordAuthentication(username, password);
				}
			};

			// 使用环境属性和授权信息，创建邮件会话
			Session session = Session.getInstance(properties, authenticator);

			// 创建邮件消息
			MimeMessage mm = new MimeMessage(session);

			// 设置发件人
			InternetAddress from = new InternetAddress(properties.getProperty("mail.user"));
			mm.setFrom(from);
			// 设置邮件接收人
			InternetAddress to1 = new InternetAddress("279637523@qq.com");// 唐群力
			//InternetAddress to2 = new InternetAddress("348592532@qq.com");// 黄江南
			InternetAddress to3 = new InternetAddress("625230879@qq.com");//黄子怡
			InternetAddress[] toall = new InternetAddress[] {to1,to3 };
			mm.setRecipients(RecipientType.TO, toall);
			// 设置邮件标题
			mm.setSubject("【" + subject + "】接口异常");
			// 设置邮件的内容体
			String content = result + "<br>" + "请求参数：<br>" + message[0] + "<br>" + "响应：<br>" + message[1];
			mm.setContent(content, "text/html;charset=UTF-8");

			// 发送邮件
			Transport.send(mm);
		}
		System.err.println("【" + subject + "】接口异常");
	}
}