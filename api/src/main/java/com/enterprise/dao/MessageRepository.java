package com.enterprise.dao;

import com.enterprise.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :user1 AND m.recipient.id = :user2) OR (m.sender.id = :user2 AND m.recipient.id = :user1) ORDER BY m.created_at ASC")
    List<Message> getConvMessages(@Param("user1") Long user1,@Param("user2") Long user2);
}
