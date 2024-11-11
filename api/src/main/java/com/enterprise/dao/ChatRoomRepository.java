package com.enterprise.dao;

import com.enterprise.entity.ChatRoom;
import com.enterprise.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findBySenderAndRecipient(Employee employee, Employee employee1);

    @Query(value = "SELECT * FROM chat_room WHERE (recipient_id = :sen AND sender_id = :rec) OR (recipient_id = :rec AND sender_id = :sen)", nativeQuery = true)
    List<ChatRoom> findChatRooms(@Param("sen") Long sen, @Param("rec") Long rec);
    @Query(value = "SELECT * FROM chat_room WHERE recipient_id = :sen OR sender_id = :sen", nativeQuery = true)
    List<ChatRoom> findUserChatRooms(@Param("sen") Long sen);
}
