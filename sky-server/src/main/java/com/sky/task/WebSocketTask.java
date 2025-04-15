package com.sky.task;

import com.sky.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class WebSocketTask {
	@Autowired
	private WebSocketServer webSocketServer;

//	@Scheduled(cron = "0/5 * * * * ?")
//	public void task(){
//		WebSocketServer.sendInfoToAll("服务端发送的消息" +
//				DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
//	}
}
