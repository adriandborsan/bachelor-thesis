package com.adriandborsan.adminback.post.services;


import com.adriandborsan.adminback.post.dto.ReportDto;
import com.adriandborsan.adminback.post.dto.ReportReviewDTO;
import com.adriandborsan.adminback.post.entities.Post;
import com.adriandborsan.adminback.post.entities.Report;
import com.adriandborsan.adminback.post.repositories.PostRepository;
import com.adriandborsan.adminback.post.repositories.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final PostRepository postRepository;

    private final MinioService minioService;
    private final ReportRepository reportRepository;

    @Transactional
    public void reviewReport(Long reportId, ReportReviewDTO violation) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new NoSuchElementException("Report not found with id " + reportId));
        if (violation.isViolation()) {
            postRepository.deleteById(report.getPostId());
        }
        report.setStatus(Report.ReportStatus.RESOLVED);
        reportRepository.save(report);
    }


    @Transactional
    public Optional<ReportDto> getOldestPendingReport() {
        Optional<Report> optionalReport = reportRepository.findFirstByStatusOrderByCreatedDateAsc(Report.ReportStatus.PENDING);
        if (!optionalReport.isPresent()) {
            return Optional.empty();
        }

        Report report = optionalReport.get();
        report.setStatus(Report.ReportStatus.UNDER_REVIEW);
        reportRepository.save(report);

        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportDto.setStatus(report.getStatus());
        reportDto.setCreatedDate(report.getCreatedDate());

        Optional<Post> byId = postRepository.findById(report.getPostId());
        if(byId.isPresent()) {
            reportDto.setPost(byId.get());
        }

        return Optional.of(reportDto);
    }
}
