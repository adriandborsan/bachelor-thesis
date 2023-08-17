export interface LogEntryResponse {
  content:          LogEntry[];
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

export interface LogEntry {
  id:         string;
  createdAt:  Date;
  receivedAt: Date;
  message:    string;
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
