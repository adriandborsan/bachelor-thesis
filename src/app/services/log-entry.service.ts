import { LogEntryResponse } from './../models/log-entry.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { LogEntry } from '../models/log-entry.model';

@Injectable({
  providedIn: 'root',
})
export class LogEntryService {
  constructor(private http: HttpClient) {}

  restApi = '/api/v1/rest/logs';
  reactiveApi = '/api/v1/reactive/logs';

  handleError(error: any) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    console.error(`Full error is: `, error);
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }

  getPosts(
    pageSize: number,
    pageNumber: number,
    sortBy: string,
    order: string
  ) {
    return this.http
      .get<LogEntryResponse>(this.restApi, {
        params: new HttpParams()
          .set('pageNumber', pageNumber)
          .set('pageSize', pageSize)
          .set('sortBy', sortBy)
          .set('order', order),
      })
      .pipe(
        catchError((error) => {
          this.handleError(error);
          return [];
        })
      );
  }

  es!: EventSource;

  getLogEntryObservable(): Observable<LogEntry> {
    return new Observable<LogEntry>((obs) => {
      const es = new EventSource(this.reactiveApi);
      es.addEventListener('message', message => {
        obs.next(JSON.parse(message.data));
    });
    });
  }

  closeStream() {
    this.es.close();
  }
}
