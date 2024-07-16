// generic-table.component.ts
import { NgFor } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-generic-table',
  templateUrl: '../templates/generic-table.component.html',
  standalone: true,
  imports: [NgFor],
  styleUrls: ['../templates/generic-table.component.css']
})
export class GenericTableComponent {
  @Input() headers: string[] = [];
  @Input() data: any[] = [];
}
