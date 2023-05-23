import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Post } from '../post.model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.scss']
})
export class PostCreateComponent {
  constructor(private postService:PostService,private fb:FormBuilder){}

  createPostForm=this.fb.group({
    "title": new FormControl("",Validators.required),
    "message": new FormControl("",Validators.required)
  });

  onFormSubmit(){
    this.postService.addPost(this.createPostForm.value as Post);
  }
}
