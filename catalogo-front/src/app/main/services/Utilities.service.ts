import { HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { NotificationService } from "./notification";

export abstract class UtilitiesService{

handleError(notify: NotificationService,router: Router,err: HttpErrorResponse) {
    let msg = '';
    switch (err.status) {
      case 0:
        msg = err.message;
        break;
        case 404: router.navigateByUrl('/404.html'); return;
      default:
        msg = `ERROR ${err.status}: ${err.error?.['title'] ?? err.statusText}.${
          err.error?.['detail'] ? ` Details: ${err.error['detail']}` : ''
        }`;
        break;
    }
    notify.add(msg);
  }
}