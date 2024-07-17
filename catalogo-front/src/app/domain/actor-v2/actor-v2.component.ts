/* eslint-disable @typescript-eslint/no-empty-function */
/* eslint-disable @angular-eslint/no-empty-lifecycle-method */
import { Component, OnInit, OnDestroy, Input, OnChanges, SimpleChanges, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LoggerService } from '../../main/services/logger/logger.service';
import { ViewModelService } from '../baseDomain';
import { NotificationService } from '../../main/services/notification';
import { ActorsDAOService } from './actor.service';



@Injectable({
  providedIn: 'root'
})
export class ActorsV2ViewModelService extends ViewModelService<any, number> {
  constructor(dao: ActorsDAOService, notify: NotificationService, out: LoggerService,
    router: Router) {
    super(dao, {}, notify, out, router)
  }
  films: Array<any> = []

  public override view(key: number): void {
    super.view(key);
    (this.dao as unknown as ActorsDAOService).films(key).subscribe({
      next: data => {
        this.films = data.map(item => ({filmId: item.key, title: item.value }));
      },
      error: err => this.handleError(err)
    });
  }
}

@Component({
  selector: 'app-actores-list',
  templateUrl: './templates/tmpl-list.component.html',
  styleUrls: ['./actor-v2.component.css']
})
export class ActoresListComponent implements OnChanges, OnDestroy {
  @Input() page = 0
  constructor(protected vm: ActorsV2ViewModelService) { }

  public get VM(): ActorsV2ViewModelService { return this.vm; }

  ngOnChanges(changes: SimpleChanges): void {
  }

  ngOnDestroy(): void { this.vm.clear(); }
}

@Component({
  selector: 'app-actores-add',
  templateUrl: './templates/tmpl-form.component.html',
  styleUrls: ['./actor-v2.component.css']
})
export class ActoresAddComponent implements OnInit {
  constructor(protected vm: ActorsV2ViewModelService) { }
  public get VM(): ActorsV2ViewModelService { return this.vm; }
  ngOnInit(): void {
    this.vm.add();
  }
}

@Component({
  selector: 'app-actores-edit',
  templateUrl: './templates/tmpl-form.component.html',
  styleUrls: ['./actor-v2.component.css']
})
export class ActoresEditComponent implements OnChanges {
  @Input() id?: string;
  constructor(protected vm: ActorsV2ViewModelService, protected router: Router) { }
  public get VM(): ActorsV2ViewModelService { return this.vm; }
  ngOnChanges(changes: SimpleChanges): void {
    if (this.id) {
      this.vm.edit(+this.id);
    } else {
      this.router.navigate(['/404.html']);
    }
  }
}

@Component({
  selector: 'app-actores-view',
  templateUrl: './templates/tmpl-view.component.html',
  styleUrls: ['./actor-v2.component.css']
})
export class ActoresViewComponent implements OnChanges {
  @Input() id?: string;
  constructor(protected vm: ActorsV2ViewModelService, protected router: Router) { }
  public get VM(): ActorsV2ViewModelService { return this.vm; }
  ngOnChanges(changes: SimpleChanges): void {
    if (this.id) {
      this.vm.view(+this.id);
    } else {
      this.router.navigate(['/404.html']);
    }
  }
}


export const ACTORES_COMPONENTES = [ActoresListComponent, ActoresAddComponent, ActoresEditComponent, ActoresViewComponent,];