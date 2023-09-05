import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class LogInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (request.url.includes('/api')) {
        return next.handle(request).pipe(catchError((error: HttpErrorResponse) => {
            if (error.status === 401) {
                this.router.navigate(['/login']);
            }
            return throwError(() => error);
        }));
    }
    return next.handle(request);
}

}
