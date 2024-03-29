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

  getOldestPendingReport(): Observable<Report | null> {
    return this.http.get<Report>(this.apiUrl, { observe: 'response' }).pipe(
      map((response: HttpResponse<Report>) => {
        if (response.status === 204) {
          return null;
        }

        const report = response.body;

        if (!report) {
          throw new Error('No report received');
        }

        report.post.files.forEach((file: PostFile) => {
          file.fullPath = environment.minioUrl + "/" + environment.bucket + file.path;
        });

        return report;
      }),
      catchError(error => {
        throw error;
      })
    );
}

}
