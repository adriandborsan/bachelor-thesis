package com.adriandborsan.adminback.ticket.services;

import com.adriandborsan.adminback.post.entities.Report;
import com.adriandborsan.adminback.ticket.entities.Ticket;
import com.adriandborsan.adminback.ticket.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTicketService {
    private final TicketRepository ticketRepository;

    public AdminTicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getOpenTickets() {
        return ticketRepository.findByStatusOrderByCreatedAt(Report.ReportStatus.PENDING);
    }

    public Ticket assignTicket(Long ticketId, Long adminId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        ticket.setAdminId(adminId);
        ticket.setStatus(Report.ReportStatus.UNDER_REVIEW);
        return ticketRepository.save(ticket);
    }

    public Ticket resolveTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        ticket.setStatus(Report.ReportStatus.RESOLVED);
        return ticketRepository.save(ticket);
    }
}
