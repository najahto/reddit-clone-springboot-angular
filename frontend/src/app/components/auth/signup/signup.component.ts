import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SignUp } from 'src/app/models/sign-up.model';
import { AuthService } from 'src/app/services/auth/auth.service';
import Validation from 'src/app/utils/validation';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  form: FormGroup = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
  });
  submitted = false;
  signUp: SignUp;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router, private toastr: ToastrService) {
    this.signUp = {
      email: '',
      username: '',
      password: ''
    };
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        username: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(20)
          ]
        ],
        email: ['', [Validators.required, Validators.email]],
        password: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(40)
          ]
        ],
        confirmPassword: ['', Validators.required],
      },
      {
        validators: [Validation.match('password', 'confirmPassword')]
      }
    );
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }


  onSubmit(): void {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.signUp.email = this.form.value.email;
    this.signUp.username = this.form.value.username;
    this.signUp.password = this.form.value.password;

    this.authService.signUp(this.signUp).subscribe({
      next: () => this.router.navigate(['login'], { queryParams: { registered: 'true' } }),
      error: () => this.toastr.error('Registration Failed! Please try again')
    }
    );

  }
}
