package interface_lib;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.common.collect.Maps;


public class LoginToken{
//	private Logger logger = LoggerFactory.getLogger(LoginToken.class);
	
//	private static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2DT4odzkDd7hMlZ7djdZQH12j38nKxriINW1MGjMry3tXheya113xwmbBOwN0GA4zTwKFauFJRzcsD0nDFq1eaatcFKeDF25R4dnQRX+4BdTwFVS8lIb8nJMluSBwK+i4Z3VF+gfZ0AqQOXda6lJ4jPBt9Ep7VXEAHXUDn9JM8wIDAQAB";
	//电商的公钥
	private static final String dsPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQWlCJKux6th72ZLa9+axSvJqvZdmUNQt2ExGcqx41Xd6ToGICPPzDdSf+Vk/LqLop2xoTz4JpxSpO8YyBNTyS77b4/Jf7Iu8p38NCS33J133sC+03deUII1ClkCwseHjaBPGkTHUH2IJgGLd6dZCrsaSGP418a70kJTb5DvoLywIDAQAB";
	//电商请求大酷狗的公钥
	private static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfyXsgO81r/7nn+LBU4D0uiQaiyipaMAbm8+vCSsO6CzdWv1GA2V0cpsJ5Doyhy1+Fs1M1TOgKa+RdxcPPeC4zvC4eDFLnpJbVzaYGyAUpjNGRMyYYvtkw+aw2VKJjpnE5DGbczm0tcsDlIT2eOJYFLLJnYHvrkJXBPmj+cVUYWwIDAQAB";
	private static final int APPID = 2101;
	private static final int CLIENTVER = 20;
	private static final String Mid = "7B3E6CA5-9F00-4940-9377-DB36257D28EC";
	private static final String APPKEY = "ulSwMbWxmyFb1MU4mLFxtvSPzeJh6xPL";
	private static final String LOGICURL = "http://login.user.kugou.com/v1/login_by_pwd";
//	private static final String LOGICURL = "http://reg.user.kugou.com/v1/reg_by_username";
	
	
	/**
	 * 根据用户名、密码获取token值
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getToken(String userName, String pwd) {
		try {
			Map<String, Object> searchMap = Maps.newHashMap();
			Date curDate = new Date();
			long clienttime = curDate.getTime()/1000;
			
			searchMap.put("appid", APPID);		
			searchMap.put("clientver", CLIENTVER);
			searchMap.put("mid", Mid);	//机器唯一码
			searchMap.put("clienttime", clienttime);
			searchMap.put("key", DigestUtils.md5Hex(APPID + APPKEY + CLIENTVER + clienttime));
			
			Map<String, Object> pwdMap = Maps.newHashMap();
			pwdMap.put("clienttime", clienttime);
			pwdMap.put("pwd", DigestUtils.md5Hex(pwd));
//			String jsonStr = JsonUtils.map2String(pwdMap);
			
			JSONObject jsonStr02 = JSONObject.fromObject(pwdMap); 
			String p = getRASEncrypt(jsonStr02.toString());
			
			
			
			
			
			
			
			searchMap.put("p", p);
			searchMap.put("username", userName);
//			String datas = JsonUtils.map2String(searchMap);
			
			
			JSONObject jsonStr01 = JSONObject.fromObject(searchMap); 
			String datas = jsonStr01.toString();
			
			
			URI uri = URI.create(LOGICURL);
			HttpEntity entity = new StringEntity(datas, ContentType.APPLICATION_JSON);
//			String result = post(uri, entity);
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(uri);
				httpPost.setEntity(entity);
				CloseableHttpResponse response = httpclient.execute(httpPost);
	            if (response.getStatusLine().getStatusCode() != 200) {
	                throw new RuntimeException("请求" + uri + " 失败:" + response.getStatusLine().getStatusCode());
	            }
				InputStream in = response.getEntity().getContent();
				
				String in2 = IOUtils.toString(in);
				
//				System.out.print(in2);
				JSONObject jo03 = JSONObject.fromObject(in2);
				//取出data字符串
				String jsonvalue04 = jo03.get("data").toString();
				
//				System.out.print(jsonvalue04);
				
				JSONObject jo04 = JSONObject.fromObject(jsonvalue04);
				
			
				String token = jo04.get("token").toString();
				String userid =jo04.get("userid").toString();				
					List<String> list = new ArrayList<String>();
					list.add(token);
					list.add(userid);
					return list;
					
				
//				String jsonvalue05 = jo04.get("token").toString();
				
//				System.out.print(jsonvalue05);
				
//				return jsonvalue05;
			
//			TokenResponse tokenResponse = JsonUtils.readValue(IOUtils.toString(in), TokenResponse.class);
//			if (tokenResponse.getStatus() == 1) {
//				return tokenResponse.getData().toString();
//			} else {
////				logger.error("error while get Token userName:{}, pwd:{}, status:{}, errorCode:{}", userName, pwd, response.getStatus(), response.getStatus());
//			}
			
		} catch (IOException e) {
//			logger.error("error while get Token e:{}",e.getMessage());
		}
		
		return null;
	}
	
	
	

	
	
	
	/**
     * 公钥加密
     * @param str
     * @return
     */
    @SuppressWarnings("static-access")
	public static String getRASEncrypt(String str) {
        try {
            // 加密
            byte[] en_content = new byte[128];
            System.arraycopy(str.getBytes(), 0, en_content, 0, str.getBytes().length);
            byte[] cipher = encrypt(loadPublicKey(PUBLICKEY), en_content);
            String encryptStr = new MD5Util().toHexString(cipher).replace(" ", "");
            // 加密后密文
            return encryptStr.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    
	public static String dsgetRASEncrypt(String str) {
        try {
            // 加密
            byte[] en_content = new byte[128];
            System.arraycopy(str.getBytes(), 0, en_content, 0, str.getBytes().length);
            byte[] cipher = encrypt(loadPublicKey(dsPUBLICKEY), en_content);
            String encryptStr = new MD5Util().toHexString(cipher).replace(" ", "");
            // 加密后密文
            return encryptStr.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
	
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }
	
	/**
     * 加密过程
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    private static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/NOPADDING");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }
	
	
}
