import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { Observable, of, startWith, switchMap } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit{
  isLoggedIn$: Observable<boolean>;
pic: string='/assets/profile.jpeg';


  setDefaultImage() {
    if (this.pic !== '/assets/profile.jpeg') {
      this.pic = '/assets/profile.jpeg';
    }
  }
  constructor(public authService: AuthService, private router: Router) {
    this.isLoggedIn$ = this.authService.isLoggedIn$;

  }

  ngOnInit() {
    this.authService.userProfileLoaded$.subscribe(profileLoaded => {
      if (profileLoaded) {
        //console.log(this.authService.getCurrentUser().profilePictureUrl);
        this.pic = this.authService.getCurrentUser().profilePictureUrl;
      }
    });
  }

  navigateToUserProfile() {
    this.router.navigate([`/users/${this.authService.getCurrentUser().id}`]);
  }

  navigateToAccountSettings() {
    this.authService.navigateToAccountSettings();
  }

  logout() {
    this.authService.logout();
  }
}
