package com.enterprise.service;

import com.enterprise.dao.ChatRoomRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dto.ChatRoomDto;
import com.enterprise.entity.ChatRoom;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatRoomService {



    private final ChatRoomRepository chatRoomRepository;
private final EmployeeRepository employeeRepository;
    private final SimpMessagingTemplate messagingTemplate;

public Optional<String>     getchatRoomById(Long Sender_id, Long Recipient_id, boolean createNewOneIfNotFound){

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
return Optional.ofNullable(chatRoomRepository.save(newChatRoom).getId());
}else{
    System.out.println(createNewOneIfNotFound &&sender.isPresent() && recipient.isPresent());
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
public ResponseEntity<Map<String, String>> sendMessage(String message, Long sender_id, Long recipient_id){
    HashMap<String,String> response = new HashMap<String, String>();

    this.getchatRoomById(sender_id,recipient_id,true);
    Optional<Employee> sender=this.employeeRepository.findById(sender_id);
    Optional<Employee> recipient=this.employeeRepository.findById(recipient_id);
    if(sender.isPresent() &&recipient.isPresent() ){
    Optional<ChatRoom> chatRoom = this.chatRoomRepository.findBySenderAndRecipient(sender.get(),recipient.get());
    Message message1 = Message.builder()
            .sender(sender.get())
            .recipient(recipient.get())
            .build();
    chatRoom.get().addMessage(message1);

        response.put("message", "YOU Have A New Message From " + sender.get().getFirstName()+" "+sender.get().getLastName());
        response.put("Status", "true");
        messagingTemplate.convertAndSend("/user/"+recipient_id+ "/queue/messages", response);;
    return ResponseEntity.ok().body(response);
    }
    response.put("message", "Something is Wrong");
    response.put("Status", "False");
    return ResponseEntity.badRequest().body(response);
}

    public List<ChatRoomDto> getRooms(Long sender_id, Long recipient_id) {
        List<ChatRoom> chatRooms = this.chatRoomRepository.findChatRooms(sender_id, recipient_id);
        List<ChatRoomDto> chatRoomsDto= chatRooms.stream().map(el ->
                ChatRoomDto.builder()
                        .id(el.getId())
                        .recipient(
                                Employee.builder()
                                        .email(el.getRecipient().getEmail())
                                        .lastName(el.getRecipient().getLastName())
                                        .id(el.getRecipient().getId())
                                        .firstName(el.getRecipient().getFirstName())
                                        .build()
                        )
                        .sender(
                                Employee.builder()
                                        .email(el.getSender().getEmail())
                                        .lastName(el.getSender().getLastName())
                                        .id(el.getSender().getId())
                                        .firstName(el.getSender().getFirstName())
                                        .build()
                        )
                        .build()
        ).collect(Collectors.toList()); // Collects mapped ChatRoomDto objects into a List
return chatRoomsDto;
    }
    public List<ChatRoomDto> getUserRooms(Long sender_id) {
        List<ChatRoom> chatRooms = this.chatRoomRepository.findUserChatRooms(sender_id);
        List<ChatRoomDto> chatRoomsDto= chatRooms.stream().map(el ->
                ChatRoomDto.builder()
                        .id(el.getId())
                        .recipient(
                                Employee.builder()
                                        .email(el.getRecipient().getEmail())
                                        .lastName(el.getRecipient().getLastName())
                                        .id(el.getRecipient().getId())
                                        .firstName(el.getRecipient().getFirstName())
                                        .build()
                        )
                        .sender(
                                Employee.builder()
                                        .email(el.getSender().getEmail())
                                        .lastName(el.getSender().getLastName())
                                        .id(el.getSender().getId())
                                        .firstName(el.getSender().getFirstName())
                                        .build()
                        )
                        .build()
        ).collect(Collectors.toList()); // Collects mapped ChatRoomDto objects into a List
        return chatRoomsDto;
    }
}
