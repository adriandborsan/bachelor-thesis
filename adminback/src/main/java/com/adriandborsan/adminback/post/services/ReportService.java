package com.adriandborsan.adminback.post.services;


import com.adriandborsan.adminback.post.dto.ReportDto;
import com.adriandborsan.adminback.post.dto.ReportReviewDTO;
import com.adriandborsan.adminback.post.entities.PostEntity;
import com.adriandborsan.adminback.post.entities.Report;
import com.adriandborsan.adminback.post.repositories.PostRepository;
import com.adriandborsan.adminback.post.repositories.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    public String getCurrentUserId() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getToken().getClaims().get("sub");
    }

    @Transactional
    public Optional<ReportDto> getOldestPendingReport() {
        Optional<Report> optionalReport = Optional.empty();
        String adminId = getCurrentUserId();
        int count = 0;

        while (!optionalReport.isPresent()) {

            // Look for a report under review by this admin
            optionalReport = reportRepository.findFirstByAdminIdAndStatus(adminId, Report.ReportStatus.UNDER_REVIEW);

            if (optionalReport.isPresent()) {
            } else {
                // If no report under review by this admin, find the oldest pending report
                optionalReport = reportRepository.findFirstByStatusOrderByCreatedDateAsc(Report.ReportStatus.PENDING);
            }

            // If there are no pending or under review reports, return an empty optional
            if (!optionalReport.isPresent()) {
                return Optional.empty();
            }

            Report report = optionalReport.get();

            // Check if the related post exists
            Optional<PostEntity> byId = postRepository.findById(report.getPostId());

            // If the related post does not exist, set this report as RESOLVED, and search again
            if (!byId.isPresent()) {
                report.setStatus(Report.ReportStatus.RESOLVED);
                reportRepository.save(report);
                optionalReport = Optional.empty();
                continue;
            } else {
            }

            // If a post exists and the report was not under review by this admin, set the report status to UNDER_REVIEW
            if (report.getStatus() != Report.ReportStatus.UNDER_REVIEW) {
                report.setStatus(Report.ReportStatus.UNDER_REVIEW);
                report.setAdminId(adminId); // Set the adminId for this report
                reportRepository.save(report);
            }
        }

        Report report = optionalReport.get();

        // Populate the reportDto with the information from the report
        ReportDto reportDto = new ReportDto();
        reportDto.setId(report.getId());
        reportDto.setStatus(report.getStatus());
        reportDto.setCreatedDate(report.getCreatedDate());

        Optional<PostEntity> byId = postRepository.findById(report.getPostId());
        if(byId.isPresent()) {
            reportDto.setPost(byId.get());
        }

        return Optional.of(reportDto);
    }
}
