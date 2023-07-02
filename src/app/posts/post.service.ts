import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable, catchError, map, of, tap, throwError } from 'rxjs';
import { PostDeleteComponent } from './post-delete/post-delete.component';
import { PostUpdateComponent } from './post-update/post-update.component';
import { Post, PostResponse,PostFile, UpdatePost } from './post.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private http: HttpClient, public dialog: MatDialog) {}

  baseUri = '/api/v1/posts';
  baseProfileUri = '/api/v1/users';
  postsModifiedEventEmitter = new EventEmitter();

  getPosts(page: number = 0, size: number = 2, sorts: {property: string, direction: string}[] = [{property: 'id', direction: 'desc'}]) {
    // Build the sort parameter
    let sortParam = sorts.map(sort => `${sort.property},${sort.direction}`).join('&sort=');

    // Build the full URL with all parameters
    let fullUrl = `${this.baseUri}?page=${page}&size=${size}&sort=${sortParam}`;

    return this.http.get<PostResponse>(fullUrl).pipe(
      map((response: PostResponse) => {
        response.content.forEach((post: Post) => {
          post.files.forEach((file: PostFile) => {
            file.fullPath = environment.minioUrl +"/"+environment.bucket+ file.path;
          });
        });
        return response;
      }),
      catchError((error) => {
        this.handleError(error);
        return of(PostResponse.defaultPostResponse());
      })
    );
  }


  getPostsByUser(userId:string,page: number = 0, size: number = 2, sorts: {property: string, direction: string}[] = [{property: 'id', direction: 'desc'}]) {
    // Build the sort parameter
    let sortParam = sorts.map(sort => `${sort.property},${sort.direction}`).join('&sort=');

    // Build the full URL with all parameters
    let fullUrl = `${this.baseProfileUri}/${userId}/posts?page=${page}&size=${size}&sort=${sortParam}`;
//console.log(fullUrl);

    return this.http.get<PostResponse>(fullUrl).pipe(
      map((response: PostResponse) => {
        //console.log(PostResponse);
        response.content.forEach((post: Post) => {
          if (post.userEntity.profilePicture && post.userEntity.profilePicture !== 'null') {
            post.userEntity.profilePicture=environment.minioUrl +"/"+environment.profileBucket+ post.userEntity.profilePicture;
          }
          //console.log(  post.userEntity.profilePicture);

          post.files.forEach((file: PostFile) => {
            file.fullPath = environment.minioUrl +"/"+environment.bucket+ file.path;
          });
        });
        return response;
      }),
      catchError((error) => {
        this.handleError(error);
        return of(PostResponse.defaultPostResponse());
      })
    );
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

  addPost(formData: FormData): Observable<Post> {
   return this.http
      .post<Post>(this.baseUri, formData)
      .pipe(  tap(() => {
        this.postsModifiedEventEmitter.emit();
      }),
        catchError((error) => this.handleError(error)))

  }

  getPost(id: number) {
    return this.http
      .get<any>(`${this.baseUri}/${id}`)
      .pipe(
        // ap(json => //console.log(JSON.stringify(json))),
      catchError((error) => this.handleError(error)));
  }

  report(id: number) {
    return this.http
      .post<any>(`${this.baseUri}/${id}/report`,{})
      .pipe(catchError((error) => this.handleError(error)))
  }

  deletePost(id: number) {
    const dialogRef = this.dialog.open(PostDeleteComponent, { data: id });

    dialogRef.afterClosed().subscribe((response) => {
      if (response === true) this.delete(id);
    });
  }

  delete(id: number) {
    return this.http
      .delete(`${this.baseUri}/${id}`)
      .pipe(catchError((error) => this.handleError(error)))
      .subscribe({
        complete: () => {
          this.postsModifiedEventEmitter.emit();
        },
        error: (error) => this.handleError(error),
      });
  }

  edit(id: number) {
    this.getPost(id).subscribe({
      next: (post) => {
        const dialogRef = this.dialog.open(PostUpdateComponent, { data: post });
        dialogRef.afterClosed().subscribe((response) => {
          if (response === undefined) return;
          this.updatePost(response, id).subscribe();
        });
      },
      error: (error) => this.handleError(error),
    });
  }

  updatePost(post: UpdatePost, id: number): Observable<Post> {
    return this.http
      .put<Post>(`${this.baseUri}/${id}`, post)
      .pipe(catchError((error) => this.handleError(error)))
      .pipe(  tap(() => {
        this.postsModifiedEventEmitter.emit();
      }),
        catchError((error) => this.handleError(error)))
  }
}
