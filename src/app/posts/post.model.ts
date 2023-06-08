export interface PostResponse {
  content:          Post[];
  pageable:         Pageable;
  totalPages:       number;
  totalElements:    number;
  last:             boolean;
  size:             number;
  number:           number;
  sort:             Sort;
  numberOfElements: number;
  first:            boolean;
  empty:            boolean;
}



export interface Post {
  id: number;
  title: string;
  message: string;
  files: PostFile[];
}


export interface UpdatePost {
  title: string;
  message: string;
  fileIdsToKeep: number[];
  files: PostFile[];
}

export interface PostFile {
  id: number
  path: string
  fullPath: string
  mimeType: string
  originalName: string
}

export interface Pageable {
  sort:       Sort;
  offset:     number;
  pageNumber: number;
  pageSize:   number;
  paged:      boolean;
  unpaged:    boolean;
}

export interface Sort {
  empty:    boolean;
  sorted:   boolean;
  unsorted: boolean;
}



export namespace PostResponse {
  export function defaultPostResponse(): PostResponse {
    return {
      content: [],
      pageable: {
        sort: { empty: true, sorted: false, unsorted: true },
        offset: 0,
        pageNumber: 0,
        pageSize: 0,
        paged: false,
        unpaged: true
      },
      totalPages: 0,
      totalElements: 0,
      last: false,
      size: 0,
      number: 0,
      sort: { empty: true, sorted: false, unsorted: true },
      numberOfElements: 0,
      first: true,
      empty: true
    };
  }
}


