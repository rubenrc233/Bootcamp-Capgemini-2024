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
  private name = 'world'
  date  = '2024-07-11'
  fontSize = 24
  list = [
    {id: 1, name: 'Madrid'},
    {id: 2, name: 'BARCELONA'},
    {id: 3, name: 'oviedo'},
    {id: 4, name: 'ciudad Real'},
  ]

  provinceId = 2
  result? : string
  visible = true
  aesthetic = {important: true, error: false, urgent: true}
  public calculate(a:number, b:number): number { return a+b}
  public add(province:string){
    const id = this.list[this.list.length-1].id + 1
    this.list.push({id,name:province})
    this.provinceId = id
  }
  private suscriptor: Unsubscribable | undefined;
  constructor(public vm: NotificationService) { }
  public get Name():string {return this.name}
  public set Name(value:string){this.name = value}

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
      public hi(): void {
        this.result = `Hi ${this.Name}`
      }
      public bye(): void {
        this.result = `Bye ${this.Name}`
      }

      public say(something: string): void {
        this.result = `Bye ${something}`
      }
      switch(){
        this.visible = !this.visible
        this.aesthetic.error = !this.aesthetic.error
        this.aesthetic.important = !this.aesthetic.important
      }
}
