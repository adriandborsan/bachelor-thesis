import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, catchError, map, mergeMap, of, throwError } from 'rxjs';
import { PostFile, Report } from './Report.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private apiUrl = '/api/v1/reports';

  constructor(private http: HttpClient) { }

  reviewReport(reportId: number, review: {violation: boolean}) {
    return this.http.post<any>(`${this.apiUrl}/${reportId}/review`, review);
  }

  getOldestPendingReport(): Observable<Report> {
    return this.http.get<Report>(this.apiUrl, { observe: 'response' }).pipe(
      mergeMap((response: HttpResponse<Report>) => {
        if (response.status === 204) {
          return throwError(() => new Error('No report to process'));
        }

        let report = response.body;

        if (!report) {
          return throwError(() => new Error('No report received'));
        }

        report.post.files.forEach((file: PostFile) => {
          file.fullPath = environment.minioUrl + "/" + environment.bucket + file.path;
        });

        return of(report);
      }),
      catchError(error => {
        // handle error scenario
        console.log(error);
        return throwError(() => error);
      })
    );
  }



}
