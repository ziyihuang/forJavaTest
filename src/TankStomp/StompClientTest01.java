package TankStomp;

/**
 * @author huangziyi@circle
 * @date 2017年3月14日 time 下午6:21:14
 * 
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
class room01{
	
	private Long kugouId;
	
	private Long roomId;
	
	public void Room01(Long kugouId, Long roomId) {
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


public class StompClientTest01 {


	public static void main(String[] args) throws InterruptedException {
		int c =2940;
//		CyclicBarrier barrier = new CyclicBarrier(c);
		 ExecutorService executor = Executors.newFixedThreadPool(c);
		
		Map<Integer, Room1> map = new HashMap<>();
		int cnt = 1;
		for (int j = 1; j <= 490; j++) {
			for (int k = 1; k <= 6; k++) {
				map.put(cnt++, new Room1((long) (100*j+k), 20000L+j));
			}
		}
		
		for (Integer key : map.keySet()) {
			Room1 room = map.get(key);
			Thread.sleep(300);
			executor.execute(new MyThread01(room.getKugouId(), room.getRoomId()));
		}
	
		executor.shutdown();
	}

	

	}

class MyThread01 implements Runnable {
	// 设置端口
	private static int port = 9000;

//	public static CountDownLatch mainlatch = new CountDownLatch(300);// 计数器
	private static SockJsClient sockJsClient;
	private static WebSocketStompClient stompClient;
	private static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
	public static StompSessionHandler handler;
	static final CountDownLatch latch = new CountDownLatch(10);// 计数器
	//private CyclicBarrier barrier;
	static CountDownLatch mainlatch;
	private Long kugouId;
	private Long roomId;

	public MyThread01( Long kugouId, Long roomId) {
		

		this.kugouId = kugouId;
		this.roomId = roomId;
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
				session.subscribe("/topic/200001", new StompSessionHandlerAdapter() {
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

//		try {
//			barrier.await();
//		} catch (InterruptedException | BrokenBarrierException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		stompClient.connect("ws://172.17.13.137:{port}/tanke/fxgame-tanke-websocket", headers, handler, port);

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

 