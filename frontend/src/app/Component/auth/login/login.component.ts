import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { JwtModule } from '@auth0/angular-jwt';
import { AuthService } from '../../../auth.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  form: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  passwordVisible: boolean = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  login(): void {
    if (this.form.valid) {
      const email = this.form.get('email')!.value;
      const password = this.form.get('password')!.value;

      this.authService.login({ email, password })
        .subscribe({
          next: (result) => {
            console.log('Login successful:', result);
            this.successMessage = 'Login successful';
            this.errorMessage = null;
            this.router.navigate(['/home']);
          },
          error: (error) => {
            console.error('Login failed:', error);
            this.errorMessage = `Login Error: ${error.error}`;
            this.successMessage = null;
            alert(`Login Error: ${error.error}`);
          },
          complete: () => {
            console.log('Login completed');
          }
        });
    }
  }
}