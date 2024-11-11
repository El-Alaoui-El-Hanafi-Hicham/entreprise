package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@ToString
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
@Column(name = "message")
    private String message;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    public Employee recipient;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_ID", referencedColumnName = "id")
    public Employee sender;
    @ManyToOne
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;
    @CreationTimestamp
    @Column(updatable = false)
    private Date created_at;
    @UpdateTimestamp
    @Column
    private Date updated_at;
}
