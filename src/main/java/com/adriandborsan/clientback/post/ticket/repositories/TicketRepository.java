package com.adriandborsan.clientback.post.ticket.repositories;

import com.adriandborsan.clientback.post.ticket.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByClientId(Long clientId);
}
