package TankStomp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import TankStomp.Tank_MyStringMessageConverter;
import net.sf.json.JSONObject;

public class StompClient {
	// 设置端口
	private static int port = 9000;
	final static CountDownLatch latch = new CountDownLatch(500);//计数器
	private static SockJsClient sockJsClient;
	private static WebSocketStompClient stompClient;
	private static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	public static void main(String[] args) throws InterruptedException {
		// sockJsClient
		List<Transport> transport = new ArrayList<Transport>();
		transport.add(new WebSocketTransport(new StandardWebSocketClient()));
		sockJsClient = new SockJsClient(transport);
		// stompClient
		stompClient = new WebSocketStompClient(sockJsClient);
		// 设置String消息转换器
		stompClient.setMessageConverter(new Tank_MyStringMessageConverter());
		// handler
		StompSessionHandlerAdapter handler = new StompSessionHandlerAdapter() {
			public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
				session.subscribe("/topic/16f6zpt", new StompSessionHandlerAdapter() {
					// handleFrame在afterConnected后执行，所以会先send
					public void handleFrame(StompHeaders headers, Object payload) {
						System.out.println("<<< MESSAGE\n" + payload);
						// latch减1
//						latch.countDown();
//						if (latch.getCount() == 0) {
//							session.disconnect();
//						}
					}
				});
				JSONObject send = new JSONObject();
				
				/* 加入游戏*/ 
				send.put("tankId", 123);
				send.put("h",  "802600647|16fc3ij");
				send.put("token", "token");
				String send1 = send.toString();
				session.send("/tk/jg", send1);
				System.out.println(">>> SEND\n" + send1);
				/* 加入游戏*/
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/* 上传控制信息*/
//				@SuppressWarnings("rawtypes")
//				List<Map> list = new ArrayList<Map>(); 
//				HashMap<String, String> map=new HashMap<String, String>();
//				map.put("c","2|2|2");
//				map.put("c1","2|2222");
//				list.add(map);
//				JSONObject send2 = new JSONObject();
//				send2.put("h",  "138|20134");
//				send2.put("t", "123|1222|1");
//				send2.put("w",  1);
//				send2.put("b", "100|100");
//				send2.put("l", list);
//				
//				String send3 = send2.toString();
//				session.send("/tk/uc", send3);
//				System.out.println(">>> SEND\n" + send3);
				
				/* 上传控制信息*/
				
				
			}
		};
		stompClient.connect("ws://172.17.13.137:{port}/tanke/fxgame-tanke-websocket1", headers, handler, port);
		// 等待latch到0
		latch.await();
	}
}