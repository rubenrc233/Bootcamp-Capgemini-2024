import { Component } from '@angular/core';

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [],
  templateUrl: './formulario.component.html',
  styleUrl: './formulario.component.css'
})
export class FormularioComponent {

  element = {id: 0, name:'Luis', surnames: 'Gonzalez', email:'exaple@example.com',age: 99, date:'2024/07/15', violent: true}
}
