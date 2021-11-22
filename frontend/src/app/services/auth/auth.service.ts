import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { map, Observable } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request.model';
import { LoginResponse } from 'src/app/models/login-response.model';
import { SignUp } from 'src/app/models/sign-up.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl = environment.baseUrl;

  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) { }

  signUp(signUp: SignUp): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/auth/signup', signUp);
  }

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.httpClient.post<LoginResponse>(this.baseUrl + '/auth/login',
      loginRequest).pipe(map(data => {
        this.localStorage.store('username', data.username);
        this.localStorage.store('authenticationToken', data.authenticationToken);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresAt', data.expiresAt);
        return true;
      }));
  }
}
