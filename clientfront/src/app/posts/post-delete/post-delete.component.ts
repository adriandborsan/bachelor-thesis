import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-post-delete',
  templateUrl: './post-delete.component.html',
  styleUrls: ['./post-delete.component.scss']
})
export class PostDeleteComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public id: number) {}
}
