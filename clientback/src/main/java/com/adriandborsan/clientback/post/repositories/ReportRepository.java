package com.adriandborsan.clientback.post.repositories;

import com.adriandborsan.clientback.post.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
