import { AuthService } from './auth/auth.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgcCookieConsentConfig, NgcCookieConsentService, NgcInitializationErrorEvent, NgcInitializingEvent, NgcNoCookieLawEvent, NgcStatusChangeEvent } from 'ngx-cookieconsent';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit {
  isLoggedIn(): Observable<boolean> {
    return this.authService.isLoggedIn$;
  }

constructor(
    private ccService: NgcCookieConsentService,
    public authService:AuthService
  ){}

ngOnInit() {
  this.ccService.statusChange$.subscribe(
    (event: any) => {
    });
    this.ccService.initialized$.subscribe(
      (event: any) => {
        this.ccService.destroy();
        this.ccService.open();
      }
    );
    this.ccService.initializing$.subscribe(
      (event: any) => {
      }
    );
    this.ccService.initializationError$.subscribe(
      (event: any) => {
      }
    );

}


}
