import { Injectable } from "@angular/core";
import { RESTDAOService } from "../baseDomain";
import { HttpContext } from "@angular/common/http";
import { Observable } from "rxjs";
import { AUTH_REQUIRED } from "../../main/services/Utilities.service";

@Injectable({
  providedIn: 'root'
})
export class ActorsDAOService extends RESTDAOService<any, number> {
  constructor() {
    super('actors/v1', { context: new HttpContext().set(AUTH_REQUIRED, false) });
  }
  films(id: number): Observable<Array<any>> {
    return this.http.get<any>(`${this.baseUrl}/${id}/films`, this.option);
  }
}
