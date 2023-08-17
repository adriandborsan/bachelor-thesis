import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, startWith, switchMap, map } from 'rxjs';
import { LogEntry } from '../models/log-entry.model';
import { LogEntryService } from '../services/log-entry.service';

@Component({
  selector: 'app-log-table',
  templateUrl: './log-table.component.html',
  styleUrls: ['./log-table.component.scss'],
})
export class LogTableComponent implements AfterViewInit {
  data: LogEntry[] = [];
  constructor(private logEntryService: LogEntryService) {}

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.logEntryService.getPosts(
            this.paginator.pageSize,
            this.paginator.pageIndex,
            this.sort.active,
            this.sort.direction
          );
        }),
        map((data) => {
          this.isLoadingResults = false;

          if (data === null) {
            return [];
          }

          this.resultsLength = data.totalElements;
          return data.content;
        })
      )
      .subscribe(data => this.data = data);
  }


  displayedColumns = ['id', 'createdAt', 'receivedAt', 'message'];
  resultsLength = 0;
  isLoadingResults = true;

  @ViewChild('paginator')
  paginator!: MatPaginator;
  @ViewChild('sorter')
  sort!: MatSort;
}
