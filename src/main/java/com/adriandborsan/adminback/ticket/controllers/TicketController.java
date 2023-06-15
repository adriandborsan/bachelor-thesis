package com.adriandborsan.adminback.ticket.controllers;

import com.adriandborsan.adminback.ticket.entities.Ticket;
import com.adriandborsan.adminback.ticket.services.AdminTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {
    private final AdminTicketService ticketService;

    public TicketController(AdminTicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> getOpenTickets() {
        return ticketService.getOpenTickets();
    }

    @PatchMapping("/{ticketId}/assign")
    public Ticket assignTicket(@PathVariable Long ticketId, @RequestParam Long adminId) {
        return ticketService.assignTicket(ticketId, adminId);
    }

    @PatchMapping("/{ticketId}/resolve")
    public Ticket resolveTicket(@PathVariable Long ticketId) {
        return ticketService.resolveTicket(ticketId);
    }
}

