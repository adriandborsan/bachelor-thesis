import { APP_INITIALIZER, NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ServiceWorkerModule } from '@angular/service-worker';
import { PostComponent } from './posts/post/post.component';
import { PostCreateComponent } from './posts/post-create/post-create.component';
import { PostReadComponent } from './posts/post-read/post-read.component';
import { PostUpdateComponent } from './posts/post-update/post-update.component';
import { PostDeleteComponent } from './posts/post-delete/post-delete.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import {MatExpansionModule} from '@angular/material/expansion';
import { MatNativeDateModule } from '@angular/material/core';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { LoginComponent } from './auth/login/login.component';
import { HomeComponent } from './home/home.component';
import { AuthService } from './auth/auth.service';
import { AuthGuard } from './auth/auth.guard';
import { environment } from 'src/environments/environment';
import { MatChipsModule } from '@angular/material/chips';
import { LogInterceptor } from './auth/log.interceptor';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { LoginGuard } from './auth/login.guard';
import { PostSkeletonComponent } from './posts/post-skeleton/post-skeleton.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {NgcCookieConsentModule, NgcCookieConsentConfig} from 'ngx-cookieconsent';
import { NavbarComponent } from './navbar/navbar.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { UserEditDialogComponent } from './user-profile/user-edit-dialog/user-edit-dialog.component';








const cookieConfig:NgcCookieConsentConfig = {
  cookie: {
    domain: 'localhost'// it is recommended to set your domain, for cookies to work properly
  },
  palette: {
    popup: {
      background: '#000'
    },
    button: {
      background: '#f1d600'
    }
  },
  theme: 'edgeless',
  type: 'opt-out',
  layout: 'my-custom-layout',
  layouts: {
    "my-custom-layout": '{{buttons}}'
  },
  elements:{
    buttons: `
    <span id="cookieconsent:desc" class="cc-message">{{message}}
     <button (click)="delclineCookies()">Decline</button>
     <button (click)="customomizeCookies()">Customize Cookies</button>
     <button (click)="acceptCookies()">Accept</button>
    </span>
    `,
  },
  content:{
    message: 'By using our site, you acknowledge that you have read and understand our '
  }
};
function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.issuer,
        realm: 'clientapp',
        clientId: environment.client
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      },
      shouldAddToken: (request) => {
        const { method, url } = request;
        const isGetRequest = 'GET' === method.toUpperCase();
        const acceptablePaths = ['/assets', '/clients/public','/login'];
        const isAcceptablePathMatch = acceptablePaths.some((path) =>
          url.includes(path)
        );

        return !(isGetRequest && isAcceptablePathMatch);
      }
    });
}

@NgModule({
  declarations: [
    AppComponent,
    PostComponent,
    PostCreateComponent,
    PostReadComponent,
    PostUpdateComponent,
    PostDeleteComponent,
    LoginComponent,
    HomeComponent,
    PostSkeletonComponent,
    NavbarComponent,
    UserProfileComponent,
    PageNotFoundComponent,
    UserEditDialogComponent
  ],
  imports: [
    NgcCookieConsentModule.forRoot(cookieConfig),
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    HttpClientModule,
    MatDialogModule,
    MatCardModule,
    MatIconModule,
    MatMenuModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatSelectModule,
    MatInputModule,
    MatExpansionModule,
    FormsModule,
    MatNativeDateModule,
    KeycloakAngularModule,
    MatChipsModule,
    CarouselModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatToolbarModule
  ],
  providers: [{
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LogInterceptor,
      multi: true
    },
    AuthService,
    AuthGuard,
    LoginGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
