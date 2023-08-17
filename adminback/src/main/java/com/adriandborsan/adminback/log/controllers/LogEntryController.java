package com.adriandborsan.adminback.log.controllers;

import com.adriandborsan.adminback.log.documents.LogEntry;
import com.adriandborsan.adminback.log.services.LogEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rest/logs")
@CrossOrigin("*")
@RequiredArgsConstructor
public class LogEntryController {

    private final LogEntryService logEntryService;

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
