import { Component, Input } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { Post } from '../Report.model';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent {

  @Input()
  post!: Post;
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
    loop: false,
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
