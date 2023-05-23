package com.adriandborsan.adminback.controllers;

import com.adriandborsan.adminback.documents.LogEntry;
import com.adriandborsan.adminback.services.LogEntryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rest/logs")
@CrossOrigin("*")
public class LogEntryController {

    private final LogEntryService logEntryService;

    public LogEntryController(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    @GetMapping("/{id}")
    public LogEntry findById(@PathVariable String id) {
        return logEntryService.findById(id);
    }

    @GetMapping
    public Page<LogEntry> findAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                  @RequestParam(defaultValue = "100", required = false) int pageSize,
                                  @RequestParam(defaultValue = "receivedAt", required = false) String sortBy,
                                  @RequestParam(defaultValue = "asc", required = false) String order) {
        return logEntryService.findAll(pageNumber, pageSize, sortBy, order);
    }
}
