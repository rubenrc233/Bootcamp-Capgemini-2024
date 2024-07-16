import { Component, forwardRef, OnDestroy, OnInit } from '@angular/core';
import { ActorViewModelService } from '../actor.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormsModule,
} from '@angular/forms';
import { ErrorMessagePipe } from '../../../../pipes/cadenas.pipe';
import { GenericTableComponent } from '../../../main/services/BaseEntity.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-actor',
  standalone: true,
  imports: [forwardRef(() => ActorsListComponent)],
  templateUrl: './actor.component.html',
  styleUrl: './actor.component.css',
})
export class ActorsComponent implements OnInit, OnDestroy {
  constructor(protected avm: ActorViewModelService) {}
  public get VM(): ActorViewModelService {
    return this.avm;
  }
  ngOnInit(): void {
    this.avm.list();
  }
  ngOnDestroy(): void {
    this.avm.clear();
  }
}

@Component({
  selector: 'app-actors-list',
  templateUrl: '../templates/list/template-actor-list.component.html',
  styleUrls: ['../templates/list/template-actor-list.component.css'],
  standalone: true,
  imports: [CommonModule,RouterLink, NgFor,GenericTableComponent],
})
export class ActorsListComponent implements OnInit, OnDestroy {
  private subscription: Subscription = new Subscription();
  public headers: string[] = ['actorId', 'firstName', 'lastName', 'lastUpdate'];
  constructor(protected vm: ActorViewModelService) {}
  public get VM(): ActorViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-contactos-add',
  templateUrl: '../templates/add/template-actor-add.component.html',
  styleUrls: ['../templates/add/template-actor-add.component.css'],
  standalone: true,
  imports: [FormsModule,ErrorMessagePipe],
})
export class ActorsAddComponent implements OnInit {
  constructor(
    protected vm: ActorViewModelService
  ) {
  }
  public get VM(): ActorViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    this.vm.add();
  }
}
