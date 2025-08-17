package com.enterprise.controller;

import com.enterprise.dao.EmployeeRepository;
import com.enterprise.dto.ChatRoomDto;
import com.enterprise.dto.MessageDto;
import com.enterprise.entity.ChatRoom;
import com.enterprise.entity.Employee;
import com.enterprise.entity.Message;
import com.enterprise.service.ChatRoomService;
import com.enterprise.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController 
@AllArgsConstructor
@RequestMapping(path = "/api")
public class MessageController {
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final EmployeeRepository employeeRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
//    @MessageMapping("/api/auth")
//    public void ProcessMessage(@Payload Message message){
//        Message m= messageService.save(message);
//        Map<String,String> notification = new HashMap<>();
//        notification.put("sender",message.sender.getFirstName()+" "+ message.getSender().getLastName());
//        notification.put("recipient",message.recipient.getFirstName()+" "+ message.getRecipient().getLastName());
//        simpMessagingTemplate.convertAndSendToUser(
//                String.valueOf(message.getRecipient().getId()),"/queue/message",
//                notification
//
//        );
//    }
    @GetMapping("/messages/{sender_id}/{recipient_id}")
    public List<MessageDto> getMessages(@PathVariable Long sender_id, @PathVariable Long recipient_id){
       return messageService.findMessages(sender_id,recipient_id);
    }
    @GetMapping("/messages/chatRooms/{sender_id}/{recipient_id}")
    public List<ChatRoomDto> findConversations(@PathVariable Long sender_id, @PathVariable Long recipient_id){
        return chatRoomService.getRooms(sender_id,recipient_id);
    }
    @GetMapping("/messages/chatRooms/user/{sender_id}")
    public List<ChatRoomDto> findUserConversations(@PathVariable Long sender_id){
        return chatRoomService.getUserRooms(sender_id);
    }
    @GetMapping("/messages/both/{user1}/{user2}")
    public List<MessageDto> getConvMessages(@PathVariable Long user1,@PathVariable Long user2){
        return this.messageService.getConvMessages(user1,user2);
    }
    @PostMapping("/messages/{sender_id}/{recipient_id}")
    public ResponseEntity<Map<String, String>> setMessages(@PathVariable(name = "sender_id") Long sender_id, @PathVariable(name = "recipient_id") Long recipient_id, @RequestParam(required = true) String message){
        HashMap<String,String> response = new HashMap<String, String>();

        Optional<Employee> sender= this.employeeRepository.findById(sender_id);
        Optional<Employee> recipient= this.employeeRepository.findById(recipient_id);
        if(sender.isPresent() && recipient.isPresent()) {
         Message message1 = Message.builder().sender(sender.get()).message(message).recipient(recipient.get()).build();
            return this.messageService.save(message1);
        }
      else{
          response.put("message", "Something is Wrong");
            response.put("Status", "False");
          return  ResponseEntity.badRequest().body(response);
        }
    }
}
