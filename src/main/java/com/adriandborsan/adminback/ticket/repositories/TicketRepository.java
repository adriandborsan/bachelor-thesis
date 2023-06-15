package com.adriandborsan.adminback.ticket.repositories;

import com.adriandborsan.adminback.post.entities.Report;
import com.adriandborsan.adminback.ticket.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatusOrderByCreatedAt(Report.ReportStatus status);
}
