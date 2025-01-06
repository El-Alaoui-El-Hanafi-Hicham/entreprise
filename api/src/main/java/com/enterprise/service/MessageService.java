package com.enterprise.service;

import com.enterprise.dao.ChatRoomRepository;
import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dao.MessageRepository;
import com.enterprise.dto.EmployeeDto;
import com.enterprise.dto.MessageDto;
import com.enterprise.entity.ChatRoom;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final EmployeeRepository employeeRepository;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    public ResponseEntity<Map<String, String>> save(Message message) {
        HashMap<String, String> response = new HashMap<String, String>();
        System.out.println("sender =================> "+message.getSender().getId());
        System.out.println("recipient========>"+ message.getRecipient().getId());
//        this.messageRepository.save(message );
        Employee sender_id = message.getSender();
        Employee recipient_id = message.getRecipient();
        String chatID = chatRoomService.getchatRoomById(sender_id.getId(), recipient_id.getId(), true)
                .orElseThrow();
        Optional<Employee> recipient = employeeRepository.findById(recipient_id.getId());
        Optional<Employee> sender = employeeRepository.findById(sender_id.getId());
        message.setChatRoom(chatRoomService.findById(chatID).get());
        message.setSender(sender.get());
        message.setRecipient(recipient.get());
        messageRepository.save(message);
        chatRoomService.setMessages(chatRoomService.findById(chatID).get(), message);

        response.put("notification", "YOU Have A New Message From " + sender.get().getFirst_name()+" "+sender.get().getLast_name());
        response.put("Status", "true");
        response.put("sender",(String.valueOf( sender.get().getId())));
        response.put("message", message.getMessage());
        response.put("date", message.getCreated_at().toString());
        messagingTemplate.convertAndSend("/user/"+recipient_id.getId()+ "/queue/messages", response);
        response.put("message", "the message was sent succesfully to "+ recipient.get().getFirst_name()+" "+recipient.get().getLast_name()+" with the id "+recipient_id.getId());
        response.put("Status", "true");
        return ResponseEntity.ok().body(response);
    }
public List<MessageDto>getConvMessages(Long user1,Long user2){
        List<MessageDto> messageDtos= new ArrayList<>();
    List<Message> messages=messageRepository.getConvMessages(user1,user2);
    messages.stream().forEach(el->{
        EmployeeDto sender = EmployeeDto.builder().id(el.getSender().getId())
                .email(el.getSender().getEmail())
                .last_name(el.getSender().getLast_name())
                .first_name(el.getSender().getFirst_name())
                .build();
        EmployeeDto recipient = EmployeeDto.builder().id(el.getRecipient().getId())
                .email(el.getRecipient().getEmail())
                .last_name(el.getRecipient().getLast_name())
                .first_name(el.getRecipient().getFirst_name())
                .build();
        MessageDto messageDto=MessageDto.builder().chat_id(el.getChatRoom().getId())
                .message(el.getMessage())
                .receiver(recipient)
                .sender(sender)
                .id(el.getId())
                .isRead(el.getIsRead())
                .date(el.getCreated_at())
                .build();
        messageDtos.add(messageDto);
    });
    return  messageDtos;
}
    public List<MessageDto> findMessages(Long Sender_id, Long Recipient_id) {
        String ChatId = String.format("%s_%s", Sender_id, Recipient_id);
        List<MessageDto> messageDtos= new ArrayList<>();
        Optional<ChatRoom> chatRoom = chatRoomService.findById(ChatId);
        List<Message> messages=chatRoom.map(ChatRoom::getMessages).orElse(new ArrayList<>());
         messages.stream().forEach(el->{
        EmployeeDto sender = EmployeeDto.builder().id(el.getSender().getId())
                .email(el.getSender().getEmail())
                .last_name(el.getSender().getLast_name())
                .first_name(el.getSender().getFirst_name())
                .build();
        EmployeeDto recipient = EmployeeDto.builder().id(el.getRecipient().getId())
                .email(el.getRecipient().getEmail())
                .last_name(el.getRecipient().getLast_name())
                .first_name(el.getRecipient().getFirst_name())
                .build();
            MessageDto messageDto=MessageDto.builder().chat_id(el.getChatRoom().getId())
                    .message(el.getMessage())
                    .receiver(recipient)
                    .sender(sender)
                    .isRead(el.getIsRead())
                    .id(el.getId())
                    .date(el.getCreated_at())
                    .build();
             messageDtos.add(messageDto);
    });
return  messageDtos;
    }
    public ResponseEntity<String> readAllMsgs(Long user1_id,Long user2_id){
       Optional<String> chatRoom1 =this.chatRoomService.getchatRoomById(user1_id,user2_id,false);
        Optional<String> chatRoom2=this.chatRoomService.getchatRoomById(user2_id,user1_id,false);
return ResponseEntity.ok("MP");
    }

}
