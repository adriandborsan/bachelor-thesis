package com.adriandborsan.adminback.ticket.repositories;

import com.adriandborsan.adminback.ticket.entities.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<TicketMessage, Long> {
    List<TicketMessage> findByTicketIdOrderByCreatedAt(Long ticketId);
}
