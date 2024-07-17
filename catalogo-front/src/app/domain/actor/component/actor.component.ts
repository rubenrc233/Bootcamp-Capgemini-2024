import {
  Component,
  forwardRef,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { ActorViewModelService } from '../actor.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule, DatePipe, NgFor, NgIf } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormsModule,
  NgForm,
} from '@angular/forms';
import { ErrorMessagePipe } from '../../../../pipes/cadenas.pipe';
import { Subscription } from 'rxjs';
import { GenericFormComponent, GenericTableComponent, GenericViewComponent } from '../../BaseEntity.service';

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
  imports: [CommonModule, RouterLink, NgFor, GenericTableComponent],
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
  selector: 'app-actor-add',
  templateUrl: '../templates/add/template-actor-add.component.html',
  styleUrls: ['../templates/add/template-actor-add.component.css'],
  standalone: true,
  imports: [
    FormsModule,
    ErrorMessagePipe,
    GenericTableComponent,
    GenericFormComponent,FormsModule,NgIf
  ],
})
export class ActorsAddComponent implements OnInit {
  headers = ['firstName', 'lastName'];

  constructor(protected vm: ActorViewModelService) {}

  public get VM(): ActorViewModelService {
    return this.vm;
  }

  handleCancel() {
    this.VM.cancel();
  }

  handleSubmit(formValue: any) {
    const actor = {
      firstName: formValue.firstName,
      lastName: formValue.lastName,
    };

    this.VM.setActor(actor);
    this.VM.send();
  }

  ngOnInit(): void {
    this.vm.add();
  }
}
@Component({
  selector: 'app-actor-view',
  templateUrl: '../templates/view/template-actor-view.component.html',
  styleUrls: ['../templates/view/template-actor-view.component.css'],
  standalone: true,
  imports: [DatePipe, GenericViewComponent],
})
export class ActorsViewComponent implements OnInit {
  public headers: string[] = ['firstName', 'lastName'];
  public data: string[] = [];
  private currentId = 1;
  constructor(protected vm: ActorViewModelService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const storedData = localStorage.getItem('actorData');
    if (storedData) {
      let values = JSON.parse(storedData);
      this.data = values.slice(1)
      this.currentId =  JSON.parse(storedData)[0]
    }

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.updateView(+id);
      }
    });
  }

  updateView(id: number): void {
    this.vm.view(id);
    setTimeout(() => {
      this.setDataFromVM();
      localStorage.setItem('actorData', JSON.stringify(this.data));
    },50);
  }

  setDataFromVM(): void {
    const actor = this.vm.Actor;
    if (actor) {
      this.data = [actor.id, actor.nombre, actor.apellidos];
    }
  }
  handleCancel() {
    this.vm.cancel();
  }

  handleSubmit(formValue: any) {
    this.vm.edit(this.currentId);
    const actor = {
      firstName: formValue.firstName,
      lastName: formValue.lastName,
      lastUpdate: Date.now()
    };

    this.vm.setActor(actor);
    this.vm.send()
  }
}