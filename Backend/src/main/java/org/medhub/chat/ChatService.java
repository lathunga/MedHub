package org.medhub.chat;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	@Autowired
	private ChatRepository chatRepository;

	public List<Chat> getChatsById(String id) {
		List<Chat> chats = new ArrayList<Chat>();
		
		chatRepository.findAll().forEach((Chat) -> {
			if (Chat.getId().equals(id)) {
				chats.add(Chat);
			}
		});

		return chats;
	}
	
	public void addChat(Chat chat) {
		chatRepository.save(chat);
	}
}
