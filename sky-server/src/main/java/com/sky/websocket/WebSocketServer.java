package com.sky.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{sid}")
@Component
public class WebSocketServer {

	// 用来存储每个客户端对应的 WebSocket 对象，键为 sid
	private static final ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	// 客户端的 sid
	private String sid;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid) {
		this.session = session;
		this.sid = sid;
		webSocketMap.put(sid, this); // 加入 map 中
		System.out.println("有新连接加入！sid 为 " + sid);
		try {
			sendMessage("连接成功");
		} catch (IOException e) {
			System.out.println("IO 异常");
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketMap.remove(sid); // 从 map 中删除
		System.out.println("有一连接关闭！sid 为 " + sid);
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端 sid " + sid + " 的消息: " + message);
		// 可以根据业务需求处理消息，这里简单回显消息
		try {
			sendMessage("你发送的消息是: " + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发生错误时调用
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误，sid 为 " + sid);
		error.printStackTrace();
	}


	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}


	/**
	 * 给指定 sid 的客户端发送消息
	 */
	public static void sendInfo(String message, String sid) {
		WebSocketServer webSocket = webSocketMap.get(sid);
		if (webSocket != null) {
			try {
				webSocket.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 群发自定义消息
	 */
	public static void sendInfoToAll(String message) {
		for (WebSocketServer item : webSocketMap.values()) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				continue;
			}
		}
	}
}    