import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserDto, UserProfile } from '../auth/login/account.model';
import { catchError, tap, throwError } from 'rxjs';
import { UserEntity } from '../posts/post.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  updateUser(id: string, user: Partial<UserEntity>) {
    const formData = new FormData();
    Object.keys(user).forEach((key) => {
      const value = user[key as keyof UserEntity];
      if (value !== null && key !== 'id' && key !== 'profilePictureFile') {
        formData.append(key, value!.toString());
      }
    });
    // Add file to FormData if it exists
    if (user.profilePictureFile) {
      formData.append('profilePicture', user.profilePictureFile);
    }
    return this.http
      .put<UserEntity>(`${this.baseUri}/${id}`, formData)
      .pipe(catchError((error) => this.handleError(error)));
  }



  baseUri = '/api/v1/users';
  constructor(private http: HttpClient) { }

  getUser(id: string) {
    return this.http
      .get<UserEntity>(`${this.baseUri}/${id}`)
      .pipe(catchError((error) => this.handleError(error)));
  }

  getUserfancy(id: string) {
    return this.http
      .get<any>(`${this.baseUri}/${id}`)

      .pipe(
        tap((hello)=>{
        }),
        catchError((error) => this.handleError(error)));
  }
  handleError(error: any) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    console.error(
      `Full error is: `,
      error
    );
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
