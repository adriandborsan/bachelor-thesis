// auth.service.ts
import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  getUsername() {

    return  this.keycloakService.loadUserProfile();
  }

  createAccountUrl() {
    return this.keycloakService.getKeycloakInstance().createAccountUrl();
}

  constructor(private keycloakService: KeycloakService) {}


  login() {
    this.keycloakService.login({
      redirectUri: window.location.origin
    });
  }

  logout() {
    this.keycloakService.logout();
  }

}
