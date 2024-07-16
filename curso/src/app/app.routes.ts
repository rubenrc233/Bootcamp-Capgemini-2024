import { Routes } from '@angular/router';
import { ContactosListComponent,ContactosAddComponent,ContactosEditComponent,ContactosViewComponent } from './contactos/componente/componente.component';

export const routes: Routes = [ 
    { path: 'contactos', children: [
    { path: '', component: ContactosListComponent},
    { path: 'add', component: ContactosAddComponent},
    { path: ':id/edit', component: ContactosEditComponent},
    { path: ':id', component: ContactosViewComponent},
    { path: ':id/:kk', component: ContactosViewComponent},
    ]},
   ];
