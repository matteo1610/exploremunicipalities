import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../../Model/user';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../Services/auth.service';

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
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  get password() { return this.form.get('password'); }

  signup(): void {
    if (this.form.invalid) {
      return;
    }

    const user: User = this.getUserFromForm();
    this.authService.signup(user)
      .subscribe({
        next: (result) => {
          console.log('Signup successful:', result);
          this.router.navigate(['/login']);
        },
        error: (error: HttpErrorResponse) => {
          alert(`Signup failed: ${error.message}`);
        },
        complete: () => {
          console.log('Signup completed');
        }
      });
  }

  private getUserFromForm(): User {
    const user = {
      id: 0,
      email: this.form.get('email')?.value,
      password: this.form.get('password')?.value,
      role: 'AUTHENTICATED_TOURIST',
      license: null,
    };
    return user;
  }
}