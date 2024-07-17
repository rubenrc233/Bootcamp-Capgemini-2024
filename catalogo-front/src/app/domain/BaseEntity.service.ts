// generic-table.component.ts
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe } from '../../pipes/cadenas.pipe';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-generic-table',
  templateUrl: '../main/templates/list/generic-table.component.html',
  standalone: true,
  imports: [NgFor,RouterLink],
  styleUrls: ['../main/templates/list/generic-table.component.css']
})
export class GenericTableComponent {

  @Input() headers: string[] = [];
  @Input() data: any[] = [];
  @Output() delete: EventEmitter<any> = new EventEmitter();
  @Output() view: EventEmitter<any> = new EventEmitter();

  onDelete(id: any) {
    this.delete.emit(id);
  }

  
  onView(id:string){
    this.view.emit(id);
  }
}

@Component({
  selector: 'app-generic-form',
  templateUrl: '../main/templates/add/generic-form.component.html',
  standalone: true,
  imports: [CommonModule,NgFor,FormsModule,NgIf,ErrorMessagePipe],
  styleUrls: ['../main/templates/add/generic-form.component.css']
})
export class GenericFormComponent {
  @Input() headers: string[] = [];
  @Output() formSubmit: EventEmitter<any> = new EventEmitter();
  @Output() formCancel: EventEmitter<void> = new EventEmitter();

  
  trackByIndex(index: number, obj: any): any {
    return index;
  }
  onSubmit() {
    this.formSubmit.emit();
  }
  
  onCancel() {
    this.formCancel.emit();
  }
}

@Component({
  selector: 'app-generic-view',
  templateUrl: '../main/templates/view/generic-view.component.html',
  standalone: true,
  imports: [CommonModule,NgFor,FormsModule,NgIf,ErrorMessagePipe],
  styleUrls: ['../main/templates/view/generic-view.component.css']
})
export class GenericViewComponent {
  @Input() headers: string[] = [];
  @Input() data: string[] = [];
  trackByIndex(index: number, obj: any): any {
    return index;
  }
}
