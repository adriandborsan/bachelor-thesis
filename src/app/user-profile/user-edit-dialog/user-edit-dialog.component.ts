import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserEntity } from 'src/app/posts/post.model';
import { UserService } from '../user.service';
import { lastValueFrom } from 'rxjs';

@Component({
  selector: 'app-user-edit-dialog',
  templateUrl: './user-edit-dialog.component.html',
  styleUrls: ['./user-edit-dialog.component.scss']
})
export class UserEditDialogComponent implements OnInit {

  editUserForm!: FormGroup;
  profilePicturePreview: string | ArrayBuffer = '';

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<UserEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserEntity,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    //console.log("asta e data");
    //console.log(this.data);


    this.initializeForm(this.data);
    // this.fetchUser();
  }

  initializeForm(user: Partial<UserEntity> = {}) {
    this.editUserForm = this.fb.group({
      displayUsername: [user.displayUsername || null, Validators.required],
      firstName: [user.firstName || null, Validators.required],
      lastName: [user.lastName || null, Validators.required],
      bio: [user.bio || null],
      profilePictureFile: [null]  // This will be a File
    });

    if (user.profilePicture) {
      this.profilePicturePreview = user.profilePicture;
    }
    //console.log(user.profilePicture);

  }

  fetchUser(): void {
    this.userService.getUser(this.data.id)
      .subscribe(user => {
        this.initializeForm(user);
      });
  }

  handleFileInput(event: Event) {
    const fileItem = (event.target as HTMLInputElement).files?.item(0);
    const reader = new FileReader();
    reader.onload = (e: any) => {
      if (e.target) {
        this.profilePicturePreview = e.target.result as string;
      }
    };

    if (fileItem) {
      reader.readAsDataURL(fileItem);
      this.editUserForm.patchValue({
        profilePictureFile: fileItem
      });
    }
  }

  async onSubmit() {
    try {
      const response = await lastValueFrom(this.userService.updateUser(this.data.id, this.editUserForm.value as Partial<UserEntity>));
      //console.log(response);
      this.dialogRef.close();
    } catch (error) {
      console.error(error);
    }
  }

}

interface FormValues {
  displayUsername: string | null;
  firstName: string | null;
  lastName: string | null;
  bio: string | null;
  profilePictureFile: File | null;
}
