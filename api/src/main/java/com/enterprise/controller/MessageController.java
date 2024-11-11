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
//        notification.put("sender",message.sender.getFirst_name()+" "+ message.getSender().getLast_name());
//        notification.put("recipient",message.recipient.getFirst_name()+" "+ message.getRecipient().getLast_name());
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
    @PostMapping("/messages")
    public ResponseEntity<Map<String, String>> setMessages( @RequestBody(required = true) Message message){
        HashMap<String,String> response = new HashMap<String, String>();

        Optional<Employee> sender= this.employeeRepository.findById(message.getSender().getId());
        Optional<Employee> recipient= this.employeeRepository.findById(message.getRecipient().getId());
        if(sender.isPresent() && recipient.isPresent())
        return this.messageService.save(message);
      else{
          response.put("message", "Something is Wrong");
            response.put("Status", "False");
          return  ResponseEntity.badRequest().body(response);
        }
    }
}
