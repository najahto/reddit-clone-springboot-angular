import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request.model';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  submitted = false;
  loginRequest: LoginRequest;
  registerSuccessMessage?: string;
  isError?: boolean;

  constructor(private formBuilder: FormBuilder, private authService: AuthService,
    private activatedRoute: ActivatedRoute, private router: Router, private toastr: ToastrService) {
    this.loginRequest = {
      username: '',
      password: ''
    }
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        username: [
          '',
          [
            Validators.required,
            Validators.minLength(3),
            Validators.maxLength(20)
          ]
        ],
        password: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(40)
          ]
        ],
      },

    );

    this.activatedRoute.queryParams.subscribe((params) => {
      if (params['registered'] !== undefined && params['registered'] === 'true') {
        this.toastr.success('Registered Successfully')
        this.registerSuccessMessage = 'Please Check your inbox for activation email '
          + 'activate your account before you Login!';
      }
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.loginRequest.username = this.form.value.username;
    this.loginRequest.password = this.form.value.password;


    this.authService.login(this.loginRequest).subscribe({
      next: () => {
        this.isError = false;
        this.router.navigateByUrl('');
        this.toastr.success('login successful')
      },
      error: () => {
        this.isError = true;
      }
    });

  }

}
