import { PostFile } from "src/app/posts/post.model";

export interface UserProfile {
  id: string;
  profilePictureUrl: string;
  username: string;
  displayUsername: string;
  firstName: string;
  lastName: string;
  bio: string;
  createdAt: Date;
  updatedAt: Date;
}


export interface UserDto {
  id: string;
  displayUsername: string;
  firstName: string;
  lastName: string;
  bio: string;
  profilePicture: PostFile;
  createdAt: Date;
  updatedAt: Date;
}
