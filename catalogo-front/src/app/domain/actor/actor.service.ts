import { Injectable } from '@angular/core';
import { RESTDAOService } from '../../main/services/RestDAO.service';

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  constructor() { }
}

@Injectable({
  providedIn: 'root',
})
export class ActorsDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('actors');
  }
}
