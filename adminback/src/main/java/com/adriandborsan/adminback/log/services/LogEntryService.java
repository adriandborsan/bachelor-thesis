package com.adriandborsan.adminback.log.services;

import com.adriandborsan.adminback.log.documents.LogEntry;
import com.adriandborsan.adminback.log.repositories.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class LogEntryService {
    private final LogEntryRepository logEntryRepository;
    private final Sinks.Many<LogEntry> logEntrySink;

    public void save(LogEntry entity) {
        logEntrySink.tryEmitNext(logEntryRepository.save(entity));
    }

    public LogEntry findById(String s) {
        return logEntryRepository.findById(s).get();
    }

    public Page<LogEntry> findAll(int pageNumber, int pageSize, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return logEntryRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
    }


}

