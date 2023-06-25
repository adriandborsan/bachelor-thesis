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
  //console.log("wowowowowowowowo");
  this.ccService.statusChange$.subscribe(
    (event: any) => {
      //console.log("AAAAAAAAAAAAAAAAAAAAAAA\n\n\n\n\n\n\nAAAAAAAAAAAAAAAAAAAAAAAAA");

     //console.log(event);

    });
    this.ccService.initialized$.subscribe(
      (event: any) => {
        //console.log("Initialized: ", event);
        this.ccService.destroy(); // Destroy previous cookie consent
        this.ccService.open();
      }
    );
    this.ccService.initializing$.subscribe(
      (event: any) => {
        //console.log("ing: ", event);
      }
    );
    this.ccService.initializationError$.subscribe(
      (event: any) => {
        //console.log("ERROR: ", event);
      }
    );

}


}
