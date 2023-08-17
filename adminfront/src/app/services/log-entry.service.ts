import { LogEntryResponse } from './../models/log-entry.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { LogEntry } from '../models/log-entry.model';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { AuthService } from '../auth/auth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LogEntryService {
  constructor(private http: HttpClient,private authService:AuthService) {}

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

  private myWebSocket?: WebSocketSubject<LogEntry>;

  async getLogEntryObservable(): Promise<Observable<LogEntry>> {
    if (!this.myWebSocket) {
      const token = await this.authService.getToken();
      this.myWebSocket = webSocket<LogEntry>({
        url: environment.websock+this.reactiveApi+ "?token=" + token,
        deserializer: msg => JSON.parse(msg.data)
      });
    }
    return this.myWebSocket.asObservable();
  }

  closeStream() {
    if (this.myWebSocket) {
      this.myWebSocket.complete();
      this.myWebSocket = undefined;
    }
  }

}
