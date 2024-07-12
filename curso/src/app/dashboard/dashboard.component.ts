import { Component } from '@angular/core';
import { DemosComponent } from '@demos';
import { HomeComponent } from '@main';
import GraficoSvgComponent from 'src/lib/my-core/components/grafico-svg/grafico-svg.component';
import { NotificationComponent } from "../main/notification/notification.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule,NotificationComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  menu = [
    {text: 'home', icon:'', component: HomeComponent},
    {text: 'demon', icon:'', component: DemosComponent},
    {text: 'chart', icon:'', component: GraficoSvgComponent}
  ]
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  current : any = this.menu[0].component
  select(index:number){
    this.current = this.menu[index].component
  }
}
