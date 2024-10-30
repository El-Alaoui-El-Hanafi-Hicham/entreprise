package com.enterprise.controller;

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

@RestController 
@AllArgsConstructor
@RequestMapping(path = "/api")
public class MessageController {
    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/api/auth")
    public void ProcessMessage(@Payload Message message){
        Message m= messageService.save(message);
        Map<String,String> notification = new HashMap<>();
        notification.put("sender",message.sender.getFirst_name()+" "+ message.getSender().getLast_name());
        notification.put("recipient",message.recipient.getFirst_name()+" "+ message.getRecipient().getLast_name());
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(message.getRecipient().getId()),"/queue/message",
                notification

        );
    }
    @GetMapping("/messages/{sender_id}/{recipient_id}")
    public List<Message> getMessages(@PathVariable Long sender_id, @PathVariable Long recipient_id){
       return messageService.findMessages(sender_id,recipient_id);
    }
    @PostMapping("/messages/{sender_id}/{recipient_id}")
    public ResponseEntity<Map<String, String>> getMessages(@PathVariable Long sender_id, @PathVariable Long recipient_id, @RequestBody(required = true) String Message){
        return chatRoomService.sendMessage(Message,sender_id,recipient_id);
    }
}
