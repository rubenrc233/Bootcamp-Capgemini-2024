import { Component } from '@angular/core';
import { DemosComponent } from '@demos';
import { AjaxWaitComponent, HomeComponent } from '@main';
import GraficoSvgComponent from 'src/lib/my-core/components/grafico-svg/grafico-svg.component';
import { NotificationComponent } from "../main/notification/notification.component";
import { CommonModule } from '@angular/common';
import { CalculatorComponent } from '../calculator/calculator.component';
import { FormularioComponent } from '../formulario/formulario.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule,NotificationComponent,AjaxWaitComponent,RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  menu = [
    {text: 'calculator', icon:'fa-solid fa-calculator', component:CalculatorComponent},
    {text: 'home', icon:'fa-solid fa-house', component: HomeComponent},
    {text: 'demo', icon:'', component: DemosComponent},
    {text: 'chart', icon:'fa-regular fa-chart-bar', component: GraficoSvgComponent},
    {text:'formulario', icon: 'fa-brands fa-wpforms', component: FormularioComponent}
  ]
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  current : any = this.menu[0].component
  select(index:number){
    this.current = this.menu[index].component
  }
  setContacts(){
    this.current = null;
  }
}
