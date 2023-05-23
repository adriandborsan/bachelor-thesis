import { Component, Input } from '@angular/core';
import { Post } from '../post.model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent {
  @Input()
  post!: Post;


  constructor(private postService:PostService){}

  delete(){
    this.postService.deletePost(this.post.id);
  }

  edit(){
    this.postService.edit(this.post.id);
  }
}
