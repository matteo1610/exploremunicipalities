import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../../auth.service';
import { User } from '../../../Model/user';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignupComponent {
  form: FormGroup;
  passwordVisible: boolean = false;

  constructor(private router: Router, private authService: AuthService, private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password1: ['', [Validators.required, Validators.minLength(8)]],
      password2: ['', [Validators.required]],
      termsAndConditions: [false, Validators.requiredTrue],
      privacyPolicy: [false, Validators.requiredTrue]
    }, {
      validators: [this.validatePasswordMatching]
    });
  }

  validatePasswordMatching(control: AbstractControl) {
    const password1 = control.get('password1')?.value;
    const password2 = control.get('password2')?.value;
    if (password1 && password2 && password1 !== password2) {
      return { 'notSame': true };
    }
    return null;
  }

  get password1() { return this.form.get('password1'); }
  get password2() { return this.form.get('password2'); }

  signup(): void {
    const user: User = this.getUserFromForm();
    this.authService.signup(user)
      .subscribe({
        next: (result) => {
          console.log('Signup successful:', result);
          this.router.navigate(['/login']);
        },
        error: (error: HttpErrorResponse) => {
          alert(`Signup failed: ${error.error}`);
        },
        complete: () => {
          console.log('Signup completed');
        }
      });
  }

  private getUserFromForm(): User {
    return {
      id: 0,
      name: this.form.get('name')?.value,
      email: this.form.get('email')?.value,
      password: this.form.get('password1')?.value
    };
  }
}