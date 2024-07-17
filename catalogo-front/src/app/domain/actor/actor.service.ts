import { Injectable } from '@angular/core';
import { RESTDAOService } from '../../main/services/RestDAO.service';
import { ModoCRUD, UtilitiesService } from '../../main/services/Utilities.service';
import { NotificationService } from '../../main/services/notification';
import { LoggerService } from '../../main/services/logger/logger.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ActorViewModelService {
  protected modo: ModoCRUD = 'list';
  protected actorList: Array<any> = [];
  protected actor: any = {};
  protected idOriginal: any = null;
  protected listURL = '';

  constructor(
    protected notify: NotificationService,
    protected out: LoggerService,
    protected dao: ActorsDAOService,
    protected router: Router
  ) {
  }

  public get Modo(): ModoCRUD {
    return this.modo;
  }
  public get ActorList(): Array<any> {
    return this.actorList;
  }
  public get Actor(): any {
    return this.actor;
  }

  public list(): void {
    this.dao.query().subscribe({
      next: (data) => {
        this.actorList = data;
        this.modo = 'list';
      },
      error: (err) => UtilitiesService.handleError(this.notify,this.router,err),
    });
  }

  clear() {
    this.actorList = [];
  }

  public add(): void {
    this.actor = {};
    this.modo = 'add';
  }
  public edit(key: any): void {
    this.dao.get(key).subscribe({
      next: (data) => {
        this.actor = data;
        this.idOriginal = key;
        this.modo = 'edit';
      },
      error: (err) => UtilitiesService.handleError(this.notify,this.router,err),
    });
  }

  public view(key: any): void {
    console.log('ID recibido en el servicio:', key); // Verificar el ID en el servicio
    this.dao.get(key).subscribe({
      next: (data) => {
        this.actor = data;
        this.modo = 'view';
      },
      error: (err) => UtilitiesService.handleError(this.notify,this.router,err),
    });
  }
  public delete(key: any): void {
    if (!window.confirm('Sure?')) {
      return;
    }
    this.dao.remove(key).subscribe({
      next: () => this.list(),
      error: (err) => UtilitiesService.handleError(this.notify,this.router,err),
    });
  }

  public cancel(): void {
    this.actor = {};
    this.idOriginal = null;
    this.router.navigateByUrl(this.listURL);
  }

  public send(): void {
    switch (this.modo) {
      case 'add':
        this.dao.add(this.actor).subscribe({
          next: () => this.cancel(),
          error: (err) => UtilitiesService.handleError(this.notify,this.router,err),
        });
        break;
      case 'edit':
        this.dao.change(this.idOriginal, this.actor).subscribe({
          next: () => this.cancel(),
          error: (err) => UtilitiesService.handleError(this.notify,this.router,err),
        });
        break;
      case 'view':
        this.cancel();
        break;
    }
  }
}

@Injectable({
  providedIn: 'root',
})
export class ActorsDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('');
  }
}
