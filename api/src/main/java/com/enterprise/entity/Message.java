package com.enterprise.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "recipient_id", referencedColumnName = "id" )
    @JsonManagedReference
    public Employee recipient;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_ID", referencedColumnName = "id" )
    @JsonManagedReference
    public Employee sender;
    private Boolean isRead;

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
