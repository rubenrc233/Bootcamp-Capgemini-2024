import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SecurityModule } from './security';
import{LoggerService, MyCoreModule} from '@my/core'

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,SecurityModule,MyCoreModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'curso';
  constructor(log:LoggerService){
    log.error("es un error");
    log.warning("es in warning");
    log.info("es un info");
    log.log("es un log")
  }
}
