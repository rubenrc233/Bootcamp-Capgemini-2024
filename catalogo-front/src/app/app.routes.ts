import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './main';
import { ActorsAddComponent, ActorsListComponent } from './domain';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'actors/list', component: ActorsListComponent },
  { path: 'actors/add', component: ActorsAddComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home' }
];
