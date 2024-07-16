// generic-table.component.ts
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe } from '../../../pipes/cadenas.pipe';

@Component({
  selector: 'app-generic-table',
  templateUrl: '../templates/list/generic-table.component.html',
  standalone: true,
  imports: [NgFor],
  styleUrls: ['../templates/list/generic-table.component.css']
})
export class GenericTableComponent {

  @Input() headers: string[] = [];
  @Input() data: any[] = [];
  @Output() deleteActor: EventEmitter<any> = new EventEmitter();

  onDelete(id: any) {
    this.deleteActor.emit(id);
  }
}

@Component({
  selector: 'app-generic-form',
  templateUrl: '../templates/add/generic-form.component.html',
  standalone: true,
  imports: [CommonModule,NgFor,FormsModule,NgIf,ErrorMessagePipe],
  styleUrls: ['../templates/add/generic-form.component.css']
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
