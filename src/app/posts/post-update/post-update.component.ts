import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Post } from '../post.model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-update',
  templateUrl: './post-update.component.html',
  styleUrls: ['./post-update.component.scss']
})
export class PostUpdateComponent {
  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<PostUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public postData: Post
  ) {
    this.editPostForm.patchValue(postData);
  }

  editPostForm = this.fb.group({
    "id": new FormControl(),
    "title": new FormControl(""),
    "message": new FormControl(""),
  });

  onFormSubmit() {
    this.dialogRef.close(this.editPostForm.value as Post);
  }

  onFormCancel() {
    this.dialogRef.close();
  }
}
