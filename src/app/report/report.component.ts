import { Component, Input, OnInit } from '@angular/core';
import { ReportService } from './report.service';
import { Post,Report } from './Report.model';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit{
  post!: Post;
  id!:number;
  constructor(private reportService: ReportService) {
    this.loadNextReport();
  }

  ngOnInit(): void {
    this.loadNextReport();
  }

  onReview(violation: boolean): void {
    this.reportService.reviewReport(this.id, {violation}).subscribe(() => {
      this.loadNextReport();
    });
  }


  private loadNextReport(): void {
    this.reportService.getOldestPendingReport().subscribe({
      next: (report: Report) => {
        console.log(report);
        this.post = report.post;
        this.id = report.id;
      },
      error: (error: any) => {
        console.log('An error occurred:', error);
        // Handle errors
      },
      complete: () => {
        console.log('No report to process');
        // Handle the no report case
      }
    });
  }


}
