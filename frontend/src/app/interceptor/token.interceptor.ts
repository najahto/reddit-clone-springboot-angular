import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpErrorResponse
} from '@angular/common/http';
import { BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';
import { LocalStorageService } from 'ngx-webstorage';
import { LoginResponse } from '../models/login-response.model';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    isTokenRefreshing = false;
    refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

    constructor(private authService: AuthService, private localStorage: LocalStorageService) { }

    intercept(httpRequest: HttpRequest<any>, httpHandler: HttpHandler): Observable<HttpEvent<any>> {

        const jwtToken = this.authService.getJwtToken();

        if (jwtToken) {
            return httpHandler.handle(this.addToken(httpRequest, jwtToken)).pipe(catchError(error => {
                if (error instanceof HttpErrorResponse
                    && error.status === 403) {
                    return this.handleAuthErrors(httpRequest, httpHandler);
                } else {
                    return throwError(error);
                }
            }));
        }

        return httpHandler.handle(httpRequest);
    }

    private addToken(httpRequest: HttpRequest<any>, jwtToken: any) {
        return httpRequest.clone({ setHeaders: { Authorization: `Bearer ${jwtToken}` } });
    }

    private handleAuthErrors(httpRequest: HttpRequest<any>, httpHandler: HttpHandler)
        : Observable<HttpEvent<any>> {
        if (!this.isTokenRefreshing) {
            this.isTokenRefreshing = true;
            this.refreshTokenSubject.next(null);

            return this.authService.refreshToken().pipe(
                switchMap((refreshTokenResponse: LoginResponse) => {
                    this.isTokenRefreshing = false;
                    this.refreshTokenSubject
                        .next(refreshTokenResponse.authenticationToken);
                    return httpHandler.handle(this.addToken(httpRequest,
                        refreshTokenResponse.authenticationToken));
                })
            )
        } else {
            return this.refreshTokenSubject.pipe(
                filter(result => result !== null),
                take(1),
                switchMap((res) => {
                    return httpHandler.handle(this.addToken(httpRequest,
                        this.authService.getJwtToken()))
                })
            );
        }
    }



}