import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationService, NotificationType } from '@commonServices';
import { Unsubscribable } from 'rxjs';

@Component({
  selector: 'app-demos',
  standalone: true,
  imports: [],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css'
})
export class DemosComponent implements OnInit,OnDestroy{
  private suscriptor: Unsubscribable | undefined;
  constructor(public vm: NotificationService) { }

  ngOnInit(): void {
    this.suscriptor = this.vm.Notification.subscribe(n => {
    if (n.Type !== NotificationType.error) { return; }
    window.alert(`Suscripcion: ${n.Message}`);
    this.vm.remove(this.vm.List.length - 1);
    });
    }

    ngOnDestroy(): void {
      if (this.suscriptor) {
      this.suscriptor.unsubscribe();
      }
      }
}
