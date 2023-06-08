import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  constructor(private authService:AuthService){}

  logout(){
    this.authService.logout();
  }

  redirectToAccountService() {
    const url = this.authService.createAccountUrl();
    window.location.href = url;
}
}
