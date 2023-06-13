package com.adriandborsan.adminback.post.repositories;

import com.adriandborsan.adminback.post.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Optional<Report> findFirstByStatusOrderByCreatedDateAsc(Report.ReportStatus status);
}
