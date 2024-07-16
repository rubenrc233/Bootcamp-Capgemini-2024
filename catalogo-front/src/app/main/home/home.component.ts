import { Component } from '@angular/core';
import { NotificationComponent } from "../notification/notification.component";
import { NotificationModalComponent } from '../notification-modal/notification-modal.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NotificationComponent, NotificationModalComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  title = "titulo";
}
