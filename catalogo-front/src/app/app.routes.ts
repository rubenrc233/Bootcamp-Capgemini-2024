import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './main';
import { ActorsAddComponent, ActorsListComponent, ActorsViewComponent } from './domain';
import { ActorViewModelService } from './domain/actor/actor.service';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'actors', component: ActorsListComponent },
  { path: 'actors/add', component: ActorsAddComponent },
  { path: 'actors/:id', component: ActorsViewComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },

];
