import { Component, OnInit } from '@angular/core';
import { Post, PostResponse } from '../post.model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-read',
  templateUrl: './post-read.component.html',
  styleUrls: ['./post-read.component.scss']
})
export class PostReadComponent implements OnInit{


  ngOnInit(): void {
    this.redreshData();
  }

  posts:Post[]=[];

  constructor(private postService:PostService){
    this.postService.postsModifiedEventEmitter.subscribe(()=> this.redreshData());
  }

  redreshData(){
    this.postService.getPosts().subscribe(postResponse=>{
      console.log(JSON.stringify(postResponse));
      this.posts=postResponse.content;
    });
  }

}
