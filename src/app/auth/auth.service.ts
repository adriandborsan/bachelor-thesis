import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { UserProfile } from './login/account.model';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../user-profile/user.service';
import { environment } from 'src/environments/environment';
import { PostFile } from '../posts/post.model';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private userProfileLoaded = new BehaviorSubject<boolean>(false);
  userProfileLoaded$ = this.userProfileLoaded.asObservable();
  currentUser!: UserProfile;

  navigateToAccountSettings() {
    let url = this.keycloakService.getKeycloakInstance().createAccountUrl();
    window.location.href =  url;
  }

  constructor(
    private keycloakService: KeycloakService,
    private http: HttpClient,
    private userService: UserService
  ) {
    this.isLoggedIn().then((loggedIn) => {
      if (loggedIn && !this.currentUser) {
        this.loadUserProfile();
      }
      this.isLoggedIn$.next(loggedIn);
    });
  }

  async login() {
    this.keycloakService.login({
      redirectUri: window.location.origin,
    });
  }

  logout() {
    this.keycloakService.logout();
  }
  async loadUserProfile() {
    let keycloakAccount = await this.keycloakService.loadUserProfile();

    this.userService.getUser(keycloakAccount.id!).subscribe((accountDto) => {
      const file: string = accountDto.profilePicture;
      let profilePictureUrl = '';
      if (file) {
        profilePictureUrl = `${environment.minioUrl}/${environment.profileBucket}${file}`;
      }


      this.currentUser = {
        id: accountDto.id,
        profilePictureUrl: profilePictureUrl,
        username: keycloakAccount.username ?? '',
        displayUsername: accountDto.displayUsername,
        firstName: accountDto.firstName,
        lastName: accountDto.lastName,
        bio: accountDto.bio,
        createdAt: new Date(accountDto.createdAt),
        updatedAt: new Date(accountDto.updatedAt),
      };
      this.userProfileLoaded.next(true);
    });
  }

  getCurrentUser() {
    return this.currentUser;
  }

  async isLoggedIn(): Promise<boolean> {
    const isLoggedIn = await this.keycloakService.isLoggedIn();
    return isLoggedIn;
  }
}
