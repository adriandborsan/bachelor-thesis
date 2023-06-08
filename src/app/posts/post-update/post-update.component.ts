import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Post, PostFile, UpdatePost } from '../post.model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-update',
  templateUrl: './post-update.component.html',
  styleUrls: ['./post-update.component.scss']
})
export class PostUpdateComponent {
  files: File[] = [];
  existingFiles: PostFile[] = [];
  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<PostUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public postData: Post
  ) {
    this.editPostForm.patchValue(postData);
    this.existingFiles=postData.files;
  }

  editPostForm = this.fb.group({
    "id": new FormControl(),
    "title": new FormControl(""),
    "message": new FormControl(""),
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

  removeExistingFile(index: number) {
    this.existingFiles.splice(index, 1);
  }

  onFormSubmit() {
    const title = this.editPostForm.get('title')?.value ?? '';
    const message = this.editPostForm.get('message')?.value ?? '';

    const formData = new FormData();
    formData.append('title', title);
    formData.append('message', message);

    this.files.forEach((file, index) => {
      formData.append('newFiles', file, file.name);
    });

    this.existingFiles.forEach((postFile: PostFile, index: number) => {
      formData.append('fileIdsToKeep', postFile.id.toString());
    });

    this.dialogRef.close(formData);
  }


  onFormCancel() {
    this.dialogRef.close();
  }
}
