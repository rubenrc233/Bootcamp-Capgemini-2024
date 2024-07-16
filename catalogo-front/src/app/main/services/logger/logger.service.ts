import { Inject,Injectable, InjectionToken, Optional } from '@angular/core';

export const ERROR_LEVEL = new InjectionToken<string>('ERROR_LEVEL')
@Injectable(
  //{providedIn: 'root'}
)
export class LoggerService {
  private readonly lvl:number
  
  constructor(@Optional() @Inject(ERROR_LEVEL) lvl?:number){
      this.lvl = lvl ?? 99
    }
  error(message: string): void {
    if(this.lvl > 0)
      console.error(message)
  }
  warning(message: string): void {
    if(this.lvl > 1)
      console.warn(message)
  }
  info(message: string): void {
    if(this.lvl > 2){
      if(console.info){
        console.info(message)
      } else {
        console.log(message)
      }
    }

  }
  log(message: string): void {
    if(this.lvl > 3)
      console.log(message);
  }

}
