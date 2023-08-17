package com.adriandborsan.adminback.log.repositories;

import com.adriandborsan.adminback.log.documents.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogEntryRepository extends MongoRepository<LogEntry, String> {
}
