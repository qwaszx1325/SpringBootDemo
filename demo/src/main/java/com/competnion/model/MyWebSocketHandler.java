package com.competnion.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

	private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// session.getUri() 會接收到ws://localhost:8080/ws?userId=321 路徑
		// 建立連線時會自動生成
		String userId = session.getUri().getQuery().split("=")[1];
		System.out.println(session.getUri());
		sessions.put(userId, session);
		System.out.println("User " + userId + " connected.");
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		System.out.println(payload);
		String[] parts = payload.split(":", 2);
		// 連線ID
		String userId = parts[0];
		// 傳遞訊息內容
		String msg = parts[1];
		System.out.println(userId);
		System.out.println(msg);

		for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
			//多人
			WebSocketSession recipientSession = entry.getValue();
			// 只回復ID並顯示在(單人)
//			WebSocketSession recipientSession = sessions.get(userId);
			if (recipientSession != null && recipientSession.isOpen()) {
				// 傳遞的訊息內容格式
				Date date = new Date();
				SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formattedDateTime = dateTimeFormat.format(date);
				recipientSession
						.sendMessage(new TextMessage(" 時間:" + formattedDateTime + "From " + userId + ": " + msg));
			} else {
				session.sendMessage(new TextMessage("User " + userId + " is not connected."));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.values().remove(session);
		System.out.println("User disconnected.");
	}
}
