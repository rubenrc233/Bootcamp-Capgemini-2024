import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SecurityModule } from './security';
import{MyCoreModule} from '@my/core'
import { HomeComponent, NotificationModalComponent } from '@main';
import { DemosComponent } from '@demos';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,SecurityModule,MyCoreModule,NotificationModalComponent,DemosComponent,HomeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {}
