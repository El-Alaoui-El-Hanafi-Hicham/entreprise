package com.enterprise.service;

import com.enterprise.dao.ChatRoomRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.entity.ChatRoom;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatRoomService {



    private final ChatRoomRepository chatRoomRepository;
private final EmployeeRepository employeeRepository;
public Optional<String> getchatRoomById(Long Sender_id, Long Recipient_id, boolean createNewOneIfNotFound){
    String chatId= String.format("%s_%s",Sender_id,Recipient_id);
    Optional <Employee> sender= employeeRepository.findById(Sender_id);
    Optional<Employee> recipient = employeeRepository.findById(Recipient_id);

   return   chatRoomRepository.findBySenderAndRecipient(sender.get(),recipient.get()).map(ChatRoom::getId).or(()->{
if(createNewOneIfNotFound && sender.isPresent() && recipient.isPresent()){
   ChatRoom newChatRoom= ChatRoom.builder()
            .id(chatId)
            .recipient(recipient.get())
            .sender(sender.get())
            .build();
chatRoomRepository.save(newChatRoom);
}
              return Optional.empty();

           }


            );

}
public Optional<ChatRoom> findById (String id){
    return chatRoomRepository.findById(id);
}
    public List<Message> setMessages(ChatRoom chatRoom, Message message){
        chatRoom.addMessage(message);
        chatRoomRepository.save(chatRoom);
        return chatRoom.getMessages();
    }
}
