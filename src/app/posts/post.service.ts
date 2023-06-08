import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { catchError, map, of, throwError } from 'rxjs';
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
  postsModifiedEventEmitter = new EventEmitter();

  getPosts() {
    return this.http.get<PostResponse>(this.baseUri).pipe(
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

  addPost(formData: FormData) {
    this.http
      .post<Post>(this.baseUri, formData)
      .pipe(catchError((error) => this.handleError(error)))
      .subscribe({
        complete: () => {
          this.postsModifiedEventEmitter.emit();
        },
        error: (error) => this.handleError(error),
      });
  }

  getPost(id: number) {
    return this.http
      .get<Post>(`${this.baseUri}/${id}`)
      .pipe(catchError((error) => this.handleError(error)));
  }

  deletePost(id: number) {
    const dialogRef = this.dialog.open(PostDeleteComponent, { data: id });

    dialogRef.afterClosed().subscribe((response) => {
      if (response === true) this.delete(id);
    });
  }

  delete(id: number) {
    return this.http
      .delete<Post>(`${this.baseUri}/${id}`)
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
          this.updatePost(response, id);
        });
      },
      error: (error) => this.handleError(error),
    });
  }

  updatePost(post: UpdatePost, id: number) {
    return this.http
      .put<UpdatePost>(`${this.baseUri}/${id}`, post)
      .pipe(catchError((error) => this.handleError(error)))
      .subscribe({
        complete: () => {
          this.postsModifiedEventEmitter.emit();
        },
        error: (error) => this.handleError(error),
      });
  }
}
