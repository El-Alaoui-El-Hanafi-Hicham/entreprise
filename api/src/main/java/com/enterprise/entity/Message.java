package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter

public class Message {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

public Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    public Employee recipient;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipe_ID", referencedColumnName = "id")
    public Employee sender;
    @ManyToOne
    @JoinColumn(name="chat_room_id", nullable=false)
    private ChatRoom chatRoom;

}
