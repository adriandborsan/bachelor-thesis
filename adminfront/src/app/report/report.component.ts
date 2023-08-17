import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { ReportService } from './report.service';
import { Post,Report } from './Report.model';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit{
  post?: Post;
  id?:number;
  constructor(private reportService: ReportService, private cdRef: ChangeDetectorRef) {
    this.loadNextReport();
  }

  ngOnInit(): void {
    this.loadNextReport();
  }

  onReview(violation: boolean): void {
    this.reportService.reviewReport(this.id!, {violation}).subscribe(() => {
      this.loadNextReport();
    });
  }


   loadNextReport(): void {
    this.reportService.getOldestPendingReport().subscribe({
      next: (report: Report | null) => {
        if (report) {
          this.post = report.post;
          this.id = report.id;
        } else {
          this.post = undefined;
          this.id = undefined;
          console.log('No report to process');
        }
        this.cdRef.detectChanges(); // manually trigger change detection
      },
      error: (error: any) => {
        console.log('An error occurred:', error);
      }
    });
  }

}
