import { Observable, throwError } from 'rxjs';
import { HttpErrorResponse, HttpEvent, HttpEventType, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class AppHttpIntercepter implements HttpInterceptor {
    constructor(private _router: Router) { }
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(map((event: HttpEvent<any>) => {
            
            if (event instanceof HttpResponse) {
                if (event.status == 401) {
                    this.logout();
                }
            }
            return event;
        }), catchError((error: HttpErrorResponse) => {
            if (error.status == 401) {
                this.logout();
            }
            return throwError(error);
        }));
    }
    private logout() {
        localStorage.removeItem('login');
        localStorage.clear();
        this._router.navigate(['/login']);
    }
}
