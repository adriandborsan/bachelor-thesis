package com.adriandborsan.clientback.ticket.controllers;

import com.adriandborsan.clientback.ticket.entities.Ticket;
import com.adriandborsan.clientback.ticket.services.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping
    public List<Ticket> getClientTickets(@RequestParam Long clientId) {
        return ticketService.getClientTickets(clientId);
    }
}

