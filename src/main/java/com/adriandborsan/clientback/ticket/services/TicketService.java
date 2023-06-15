package com.adriandborsan.clientback.ticket.services;

import com.adriandborsan.clientback.post.entities.Report;
import com.adriandborsan.clientback.ticket.entities.Ticket;
import com.adriandborsan.clientback.ticket.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        ticket.setStatus(Report.ReportStatus.PENDING);
        ticket.setCreatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getClientTickets(Long clientId) {
        return ticketRepository.findByClientId(clientId);
    }
}
