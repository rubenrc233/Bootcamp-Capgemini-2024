import { Component } from '@angular/core';
import { NotificationService } from '../common-services';

@Component({
  selector: 'app-demos',
  standalone: true,
  imports: [],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css'
})
export class DemosComponent {
  constructor(public vm: NotificationService) { }

}
