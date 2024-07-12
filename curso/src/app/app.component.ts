import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SecurityModule } from './security';
import { MyCoreModule } from '@my/core';
import { HomeComponent, NotificationModalComponent } from '@main';
import { DemosComponent } from '@demos';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeaderComponent } from './main/header/header.component';
import { NavbarComponent } from './main/navbar/navbar.component';
import { FooterComponent } from './main/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    SecurityModule,
    MyCoreModule,
    NotificationModalComponent,
    DemosComponent,
    HomeComponent,
    DashboardComponent,
    HeaderComponent,
    NavbarComponent,
    FooterComponent  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{}
