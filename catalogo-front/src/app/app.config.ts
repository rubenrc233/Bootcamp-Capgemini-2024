import { registerLocaleData } from '@angular/common';
import localeEs from '@angular/common/locales/es';
import localeEsExtra from '@angular/common/locales/extra/es';
registerLocaleData(localeEs, 'es', localeEsExtra);

import { ApplicationConfig, provideZoneChangeDetection, LOCALE_ID } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { environment } from '../environments/enviroment';
import { LoggerService } from './main/services/logger/logger.service';
import { ajaxWaitInterceptor } from './main';

export const appConfig: ApplicationConfig = {
  providers: [
    LoggerService,
    {provide: LOCALE_ID, useValue: 'es-ES'},
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    //{ provide: HTTP_INTERCEPTORS, useClass: AjaxWaitInterceptor, multi: true, },
    //{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true, },
    provideHttpClient(withInterceptorsFromDi(), withInterceptors([ ajaxWaitInterceptor ])),
  ]
};