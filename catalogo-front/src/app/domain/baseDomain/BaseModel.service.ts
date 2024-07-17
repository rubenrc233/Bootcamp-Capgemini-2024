import { HttpErrorResponse } from '@angular/common/http';
import { ModoCRUD } from '../../main/services/Utilities.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../main/services/notification';
import { LoggerService } from '../../main/services/logger/logger.service';
import { RESTDAOService } from './RESTDAOService.service';

export abstract class ViewModelService<T, K> {
  protected mode: ModoCRUD = 'list';
  protected items: Array<T> = [];
  protected item: T | null = null;
  protected readonly initialItem: T;
  protected originalId: K | null = null;

  constructor(protected dao: RESTDAOService<T, K>, initialItem: T, public notify: NotificationService, protected logger: LoggerService,
    protected router: Router) {
    this.initialItem = initialItem;
  }

  public get Mode(): ModoCRUD { return this.mode; }
  public get Items(): Array<T> { return this.items; }
  public get Item(): T { return this.item ?? {} as T; }

  public list(): void {
    this.dao.query().subscribe({
      next: data => {
        this.items = data;
        this.mode = 'list';
      },
      error: err => this.handleError(err)
    });
  }
  
  public add(): void {
    this.loadLists();
    this.item = { ...this.initialItem };
    this.mode = 'add';
  }

  public edit(key: K): void {
    this.loadLists();
    this.dao.get(key).subscribe({
      next: data => {
        this.item = data;
        this.originalId = key;
        this.mode = 'edit';
      },
      error: err => this.handleError(err)
    });
  }

  public view(key: K): void {
    this.dao.get(key).subscribe({
      next: data => {
        this.item = data;
        this.mode = 'view';
      },
      error: err => this.handleError(err)
    });
  }

  public delete(key: K, nextFn = this.cancel): void {
    if (!window.confirm('Are you sure?')) { return; }

    this.dao.remove(key).subscribe({
      next: () => {
        nextFn.apply(this);
      },
      error: err => this.handleError(err)
    });
  }

  public clear(): void {
    this.item = null;
    this.originalId = null;
    this.items = [];
  }

  public cancel(): void {
    this.item = null;
    this.originalId = null;
  }

  public send(): void {
    switch (this.mode) {
      case 'add':
        if (this.item) {
          this.dao.add(this.item).subscribe({
            next: () => this.cancel(),
            error: err => this.handleError(err)
          });
        }
        break;
      case 'edit':
        if (this.originalId && this.item) {
          this.dao.change(this.originalId, this.item).subscribe({
            next: () => this.cancel(),
            error: err => this.handleError(err)
          });
        }
        break;
      case 'view':
        this.cancel();
        break;
    }
  }

  protected handleError(err: HttpErrorResponse): void {
    let msg = '';
    switch (err.status) {
      case 0: 
        msg = err.message; 
        break;
      case 404: 
        msg = `ERROR: ${err.status} ${err.statusText}`; 
        break;
      default:
        msg = `ERROR: ${err.status} ${err.statusText}.${err.error?.['title'] ? ` Details: ${err.error['title']}` : ''}`;
        break;
    }
    this.notify.add(msg);
  }
  protected loadLists(): void {}
}
