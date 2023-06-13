import { AuthService } from './../../auth/auth.service';
import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../post.model';
import { PostService } from '../post.service';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit{
  username: string| undefined="";
  @Input()
  post!: Post;

  constructor(private snackBar: MatSnackBar,private postService:PostService,private authService:AuthService){}
  async ngOnInit(): Promise<void> {
    this.username = (await this.authService.getUsername()).username;
  }

  delete(){
    this.postService.deletePost(this.post.id);
  }

  edit(){
    this.postService.edit(this.post.id);
  }

  report() {
    this.postService.report(this.post.id).subscribe((value: any) => {
      this.snackBar.open('Post reported successfully!', 'Close', {
        duration: 2000,
      });
    });
    }

  public getFileType(mimeType: string): string {
    if (mimeType.startsWith('image')) {
      return 'image';
    } else if (mimeType.startsWith('video')) {
      return 'video';
    } else if (mimeType.startsWith('audio')) {
      return 'audio';
    } else {
      return 'other';
    }
  }


  customOptions: OwlOptions = {
    loop: true,
    mouseDrag: false,
    touchDrag: false,
    pullDrag: false,
    dots: true,
    navSpeed: 700,
    navText: ['previous', 'next'],
    responsive: {
      0: {
        items: 1
      },
      400: {
        items: 1
      },
      740: {
        items: 1
      },
      940: {
        items: 1
      }
    },
    nav: true
  }
}
