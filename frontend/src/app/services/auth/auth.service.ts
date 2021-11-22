import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SignUp } from 'src/app/models/SignUp.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl = environment.baseUrl;

  constructor(private httpClient: HttpClient) { }

  signUp(signUp: SignUp): Observable<any> {
    return this.httpClient.post(this.baseUrl + '/auth/signup', signUp);
  }
}
