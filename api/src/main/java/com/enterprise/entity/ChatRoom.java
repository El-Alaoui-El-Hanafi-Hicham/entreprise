package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ChatRoom {
    @Id
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_ID", referencedColumnName = "id")
    private Employee recipient;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_ID", referencedColumnName = "id")
    private Employee sender;
    @OneToMany(mappedBy="chatRoom")
    private List<Message> messages;


    public List<Message> addMessage(Message message){
if(this.messages==null||this.messages.isEmpty()){
    this.messages= new ArrayList<>();
}
this.messages.add(message);
message.setChatRoom(this);
    return this.messages;
    }
}
