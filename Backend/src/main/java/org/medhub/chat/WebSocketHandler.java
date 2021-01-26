package org.medhub.chat;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {
	
	@Autowired
	private ChatService chatService;
	private String uniID = "@";
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		String msg = String.valueOf(message.getPayload());
		System.out.println(msg);
		
		// Used as a "controller" for getting all chats.
		if (msg.equals(uniID)) {
			String chatID = msg.substring(uniID.length());
			try {
				List<Chat> chatlist = chatService.getChatsById(chatID);
				for (int i = 0; i < chatlist.size(); i++) {
					session.sendMessage(new TextMessage(chatlist.get(i).getName() + ":" + chatlist.get(i).getMessage()));
				}
			} catch (NullPointerException e) {
				
			}
		} else {
			String[] items = msg.split(uniID);
		
			Chat tempChat = new Chat(UUID.randomUUID().toString(), items[0], items[1], items[2], items[3], items[4]);
			
			try {
				chatService.addChat(tempChat);
			} catch (NullPointerException e) {
				System.out.println(e.getStackTrace());
			}

			
			session.sendMessage(new TextMessage(items[3] + ":" + items[2]));
		}	
	}
}
