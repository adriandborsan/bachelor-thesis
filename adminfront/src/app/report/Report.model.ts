export interface Report {
  id: number;
  status: ReportStatus;
  post: Post;
  createdDate: Date;
}

export enum ReportStatus {
  PENDING = 'PENDING',
  UNDER_REVIEW = 'UNDER_REVIEW',
  RESOLVED = 'RESOLVED'
}



export interface Post {
  id: number;
  title: string;
  message: string;
  files: PostFile[];
}

export interface PostFile {
  id: number
  path: string
  fullPath: string
  mimeType: string
  originalName: string
}



