package com.enterprise.dao;

import com.enterprise.entity.ChatRoom;
import com.enterprise.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findBySenderAndRecipient(Employee employee, Employee employee1);
}
