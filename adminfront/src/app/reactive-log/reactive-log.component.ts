import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { map, merge, startWith, Subscription, switchMap, throwError } from 'rxjs';
import { LogEntry } from '../models/log-entry.model';
import { LogEntryService } from '../services/log-entry.service';

@Component({
  selector: 'app-reactive-log',
  templateUrl: './reactive-log.component.html',
  styleUrls: ['./reactive-log.component.scss'],
})
export class ReactiveLogComponent implements AfterViewInit, OnDestroy {
  displayedColumns = ['id', 'createdAt', 'receivedAt', 'message'];
  private sseStream!: Subscription;
length=1000;
pageSize= 10;
  constructor(private logEntryService: LogEntryService) {}
  ngOnDestroy(): void {
    if (this.sseStream) {
      this.sseStream.unsubscribe();
    }
    this.logEntryService.closeStream();
  }
  ngAfterViewInit(): void {
    this.createEventSourceObservable();
    this.createMergeSort();

  }
  createMergeSort() {
    this.dataSource.paginator=this.paginator;
    this.dataSource.sort=this.sort;
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));
  }
  dataSource: MatTableDataSource<LogEntry> = new MatTableDataSource();

  async createEventSourceObservable() {
  (await this.logEntryService.getLogEntryObservable()).subscribe(
    message=>{
      let logEntries=this.dataSource.data;
      if(logEntries.length>=this.length)
        logEntries.splice(this.length-1);
      logEntries.unshift(message);
      this.dataSource._updateChangeSubscription();
    }
  );
}

@ViewChild('paginator')
paginator!: MatPaginator;
@ViewChild('sorter')
sort!: MatSort;


lengthChange(){
  let logEntries=this.dataSource.data;
  if(logEntries.length>=this.length)
  logEntries.splice(this.length);
  this.dataSource._updateChangeSubscription();
}
pageChange(){
  this.paginator.pageIndex = 0;
  this.dataSource._updateChangeSubscription();
}
}


