package com.enterprise.service;

import com.enterprise.dao.ChatRoomRepository;
import com.enterprise.dao.MessageRepository;
import com.enterprise.entity.ChatRoom;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
private  final ChatRoomService chatRoomService;
    public Message save(Message message){
        this.messageRepository.save(message );
        Employee sender= message.getSender();
        Employee recipent= message.getRecipient();
String chatID=chatRoomService.getchatRoomById(sender.getId(),recipent.getId(),true)
        .orElseThrow();
message.setChatRoom(chatRoomService.findById(chatID).get());
chatRoomService.setMessages(chatRoomService.findById(chatID).get(),message);
        return message;
    }
public List<Message> findMessages(Long Sender_id,Long Recipient_id){
        String ChatId = String.format("%s_%s",Sender_id,Recipient_id);
Optional<ChatRoom>  chatRoom=       chatRoomService.findById(ChatId);
return chatRoom.map(ChatRoom::getMessages).orElse(new ArrayList<>());

}

}
