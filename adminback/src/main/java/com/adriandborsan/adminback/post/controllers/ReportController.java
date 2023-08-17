package com.adriandborsan.adminback.post.controllers;


import com.adriandborsan.adminback.post.dto.ReportDto;
import com.adriandborsan.adminback.post.dto.ReportReviewDTO;
import com.adriandborsan.adminback.post.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/{report_id}/review")
    public void reviewReport(@PathVariable("report_id") Long reportId, @RequestBody ReportReviewDTO review) {
        reportService.reviewReport(reportId, review);
    }

    @GetMapping
    public ResponseEntity<ReportDto> getOldestPendingReport() {
        Optional<ReportDto> reportDto = reportService.getOldestPendingReport();
        return reportDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
