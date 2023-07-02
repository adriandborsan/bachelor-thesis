import { UserService } from './user.service';
import { AuthService } from './../auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserEntity } from '../posts/post.model';
import { Observable, catchError, of, startWith, switchMap } from 'rxjs';
import { UserEditDialogComponent } from './user-edit-dialog/user-edit-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent {
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
  constructor(private dialog: MatDialog,private route: ActivatedRoute,public authService:AuthService,private userService:UserService, private router: Router) {
    this.userId = this.route.snapshot.paramMap.get('id') ?? authService.getCurrentUser().id;

    userService.getUser(this.userId).pipe(
     catchError((err) => {
         console.error(err);
         this.router.navigate(['404']);
         throw err;
     })
    ).subscribe(user=>{
     this.userEntity=user;
    //  console.log(this.userEntity.profilePicture);

     if (this.userEntity.profilePicture && this.userEntity.profilePicture !== 'null') {
       this.userEntity.profilePicture = `${environment.minioUrl}/${environment.profileBucket}${ this.userEntity.profilePicture}`;
      //  console.log(this.userEntity.profilePicture);

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

}
