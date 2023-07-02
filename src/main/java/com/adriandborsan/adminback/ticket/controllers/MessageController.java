package com.adriandborsan.adminback.ticket.controllers;

import com.adriandborsan.adminback.ticket.entities.TicketMessage;
import com.adriandborsan.adminback.ticket.services.TicketMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {
    private final TicketMessageService ticketMessageService;

    public MessageController(TicketMessageService ticketMessageService) {
        this.ticketMessageService = ticketMessageService;
    }

    @PostMapping
    public TicketMessage createMessage(@RequestBody TicketMessage message) {
        return ticketMessageService.createMessage(message);
    }

    @GetMapping
    public List<TicketMessage> getTicketMessages(@RequestParam Long ticketId) {
        return ticketMessageService.getTicketMessages(ticketId);
    }
}