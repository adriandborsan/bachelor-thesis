import { UserService } from './user.service';
import { AuthService } from './../auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserEntity } from '../posts/post.model';
import { Observable, of, startWith, switchMap } from 'rxjs';
import { UserEditDialogComponent } from './user-edit-dialog/user-edit-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit{
editProfile() {
  const dialogRef = this.dialog.open(UserEditDialogComponent, {
    data: this.userEntity
  });

  dialogRef.afterClosed().subscribe(result => {
    //console.log(`Dialog result: ${result}`);
  });
}
  userId: string;
  userEntity?:UserEntity;
  constructor(private dialog: MatDialog,private route: ActivatedRoute,public authService:AuthService,private userService:UserService) {
     this.userId = this.route.snapshot.paramMap.get('id') ?? authService.getCurrentUser().id;

    //  userService.getUserfancy(this.userId).subscribe(hello=>//console.log(hello))
     userService.getUser(this.userId).subscribe(user=>{this.userEntity=user
      //console.log("in user profile component:", this.userEntity.profilePicture);

      if (this.userEntity.profilePicture && this.userEntity.profilePicture !== 'null') {
        //console.log(this.userEntity.profilePicture);
        this.userEntity.profilePicture = `${environment.minioUrl}/${environment.profileBucket}${ this.userEntity.profilePicture}`;
        this.pic = this.userEntity.profilePicture;

      }
    });
   }
   pic: string = '/assets/profile.jpeg';
   setDefaultImage() {
     if (this.pic !== '/assets/profile.jpeg') {
       this.pic = '/assets/profile.jpeg';
     }
   }
  ngOnInit() {

  // this.userService.getUser(this.userId).subscribe(
  //   (user) => {
  //     this.user = user;
  //   },
  //   (error) => {
  //     this.router.navigate(['/404']);
  //   }
  // );
  }
}
