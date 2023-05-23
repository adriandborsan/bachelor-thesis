package com.adriandborsan.adminback.repositories;

import com.adriandborsan.adminback.documents.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogEntryRepository extends MongoRepository<LogEntry, String> {
}
