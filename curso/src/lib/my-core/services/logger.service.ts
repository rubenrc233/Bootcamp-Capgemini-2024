import { Injectable } from '@angular/core';

@Injectable(
  //{providedIn: 'root'}
)
export class LoggerService {
  error(message: string): void {
    console.error(message)
  }
  warning(message: string): void {
    console.warn(message)
  }
  info(message: string): void {
    if(console.info){
      console.info(message)
    } else {
      console.log(message)
    }
  }
  log(message: string): void {
    console.log(message);
  }

}
