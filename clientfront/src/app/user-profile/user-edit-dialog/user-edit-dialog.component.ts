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

  // Form group for the user edit form.
  editUserForm!: FormGroup;

  // Variable to store and display a preview of the profile picture.
  profilePicturePreview: string | ArrayBuffer = '';

  constructor(
    private fb: FormBuilder,

    // MatDialogRef provides a handle on the opened dialog. It can be used to close the dialog.
    public dialogRef: MatDialogRef<UserEditDialogComponent>,

    // MAT_DIALOG_DATA is a constant used to access data passed to the dialog. In this case, user details.
    @Inject(MAT_DIALOG_DATA) public data: UserEntity,

    // Service for user related operations, e.g., fetch or update user details.
    private userService: UserService
  ) {}

  // Lifecycle hook that is called after data-bound properties are initialized.
  ngOnInit(): void {
    // Initialize the form with the user data passed to the dialog.
    this.initializeForm(this.data);
  }

  // Sets up the form group with controls for each user property.
  initializeForm(user: Partial<UserEntity> = {}) {
    this.editUserForm = this.fb.group({
      displayUsername: [user.displayUsername || null, Validators.required], // Required username control.
      firstName: [user.firstName || null, Validators.required],             // Required first name control.
      lastName: [user.lastName || null, Validators.required],               // Required last name control.
      bio: [user.bio || null],                                              // Optional bio control.
      profilePictureFile: [null]  // Placeholder for the profile picture file input.
    });

    // If there's a profile picture in the provided user data, set it to the preview variable.
    if (user.profilePicture) {
      this.profilePicturePreview = user.profilePicture;
    }
  }

  // Fetches user data using its ID and updates the form with the fetched data.
  fetchUser(): void {
    this.userService.getUser(this.data.id)
      .subscribe(user => {
        this.initializeForm(user);
      });
  }

  // Triggered when a new profile picture is selected.
  handleFileInput(event: Event) {
    // Get the selected file.
    const fileItem = (event.target as HTMLInputElement).files?.item(0);

    // FileReader is used to read the contents of the file.
    const reader = new FileReader();

    reader.onload = (e: any) => {
      // When file is read, set the result as the profile picture preview.
      if (e.target) {
        this.profilePicturePreview = e.target.result as string;
      }
    };

    if (fileItem) {
      // Start reading the file as Data URL so it can be previewed.
      reader.readAsDataURL(fileItem);

      // Update the form control value with the file.
      this.editUserForm.patchValue({
        profilePictureFile: fileItem
      });
    }
  }

  // Triggered when the edit form is submitted.
  async onSubmit() {
    try {
      // Attempt to update the user with the form data.
      const response = await lastValueFrom(this.userService.updateUser(this.data.id, this.editUserForm.value as Partial<UserEntity>));

      // If successful, close the dialog.
      this.dialogRef.close();
    } catch (error) {
      // Log any error that occurs.
      console.error(error);
    }
  }

}

// TypeScript interface that describes the shape of the form values. Helps with strong typing.
interface FormValues {
  displayUsername: string | null;
  firstName: string | null;
  lastName: string | null;
  bio: string | null;
  profilePictureFile: File | null;
}
