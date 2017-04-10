package TankStomp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;


import net.sf.json.JSONObject;

public class StompClient3 {
	// 设置端口
	private static int port = 9000;
	
	public static CountDownLatch mainlatch = new CountDownLatch(2);// 计数器

	private static SockJsClient sockJsClient;
	private static WebSocketStompClient stompClient;
	private static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
	public static StompSessionHandler handler;

	public static void main(String[] args) throws InterruptedException {
//	int  count2=0;
//     
//		Map<Integer, Room1> map = new HashMap<>();
//		int cnt = 1;
//		for (int j = 1; j <= 100; j++) {
//			for (int k = 1; k <= 6; k++) {
//		
//				map.put(cnt++, new Room1((long) (100*j+k), 20000L+j));
//		
//			}
//			count2++;
//		
//		}
//		System.out.println("房间数："+count2);
//		for (Integer key : map.keySet()) {
//			Room1 room = map.get(key);
////			Thread.sleep(100);
//			MyThread a = new MyThread(mainlatch,room.getKugouId(), room.getRoomId());
//			a.start();
//	
//			
//		}
	
		for (int i = 0; i < 100; i++) {
			
			MyThread a = new MyThread(mainlatch);
			Thread.sleep(300);
			a.start();
		}

		mainlatch.await();
	}

	static class MyThread extends Thread {

		static final CountDownLatch latch = new CountDownLatch(3);// 计数器

		static CountDownLatch mainlatch;

        private Long kugouId;
		private Long roomId;
		
		public MyThread(CountDownLatch mainlatch,Long kugouId, Long roomId) {
			this.mainlatch = mainlatch;
			this.kugouId = kugouId;
			this.roomId = roomId;
		}
		public MyThread(CountDownLatch mainlatch) {
			this.mainlatch = mainlatch;
			
		}
		@Override
		public void run() {
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
					session.subscribe("/topic/20269", new StompSessionHandlerAdapter() {
						// handleFrame在afterConnected后执行，所以会先send
						public void handleFrame(StompHeaders headers, Object payload) {
							System.out.println("<<< MESSAGE\n" + payload);
							// latch减1
							latch.countDown();
							if (latch.getCount() == 0) {
								session.disconnect();
							}
						}
					});
					int kugouId=(int) Math.ceil(Math.random()*10000000);
					int roomId=(int) Math.ceil(Math.random()*10000000);
					String h = kugouId + "|" + roomId;
					JSONObject send = new JSONObject();
					send.put("tankId", 123);
					send.put("h", h);
					send.put("token", "adslfjlasfljaslfjlajf");
					String send1 = send.toString();
					session.send("/tk/jg", send1);
					// session.send("/tanke/uploadControlInfo", send1);
					System.out.println(">>> SEND\n" + send1);

				}

			};

			stompClient.connect("ws://172.17.13.142:{port}/tanke/fxgame-tanke-websocket", headers, handler, port);
			// 等待latch到0
			try {
				latch.await();
				mainlatch.countDown();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}


class Room1 {
	
	private Long kugouId;
	
	private Long roomId;
	
	public Room1(Long kugouId, Long roomId) {
		this.kugouId = kugouId;
		this.roomId = roomId;
	}

	public Long getKugouId() {
		return kugouId;
	}

	public void setKugouId(Long kugouId) {
		this.kugouId = kugouId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
}
