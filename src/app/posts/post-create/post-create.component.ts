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
  files: File[] = [];
  constructor(private postService:PostService,private fb:FormBuilder){}

  createPostForm=this.fb.group({
    "title": new FormControl("",Validators.required),
    "message": new FormControl("",Validators.required)
  });

  onFileChange(event: Event) {
    const eventObj = event as unknown as InputEvent;
    const target = eventObj.target as HTMLInputElement;
    const files = target.files as FileList;
    for (let i = 0; i < files.length; i++) {
      this.files.push(files[i]);
    }
  }

  removeFile(index: number) {
    this.files.splice(index, 1);
  }


onFormSubmit() {
  const title = this.createPostForm.get('title')?.value ?? '';
  const message = this.createPostForm.get('message')?.value ?? '';

  const formData = new FormData();
  formData.append('title', title);
  formData.append('message', message);

  this.files.forEach((file, index) => {
    formData.append('files', file, file.name);
  });

  this.postService.addPost(formData);
}
}
