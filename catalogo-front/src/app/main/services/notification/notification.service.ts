import { Injectable, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { LoggerService } from '../logger/logger.service';

export enum NotificationType { error = 'error', warn = 'warn', info = 'info', log= 'log' }

export class Notification {
  constructor(private id: number, private message: string,
  private type: NotificationType) {}
  public get Id() { return this.id; }
  public get Message() { return this.message; }
  public get Type() { return this.type; }
 }
 
@Injectable({providedIn: 'root'})
export class NotificationService implements OnDestroy{

  private notification$ = new Subject<Notification>();
  public get Notification() { return this.notification$; }

  public readonly NotificationType = NotificationType
  private list: Notification[] = [];
  constructor(private out:LoggerService){}
  public get List(): Notification[]
  { return Object.assign([], this.list); }
  public get HayNotificaciones() { return this.list.length > 0; }
  
  public add(msg: string, type: NotificationType = NotificationType.error) {
    if (!msg || msg === '') {
    this.out.error('Falta el mensaje de notificación.');
    return;
    }
    const id = this.HayNotificaciones ?
    (this.list[this.list.length - 1].Id + 1) : 1;
    const n = new Notification(id, msg, type);
    this.list.push(n);
    this.notification$.next(n);
    }
  public remove(index: number) {
    if (index < 0 || index >= this.list.length) {
    this.out.error('Index out of range.');
    return;
    }
    this.list.splice(index, 1);
  }

  public clear(){
    if(this.HayNotificaciones){
      this.list.splice(0)
    }
  }

  ngOnDestroy(): void {
    this.notification$.complete()
    }

}
