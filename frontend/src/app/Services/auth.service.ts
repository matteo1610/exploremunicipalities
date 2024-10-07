import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {JwtHelperService} from '@auth0/angular-jwt';
import { environment } from '../../environments/environment.development';



@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public authenticated: boolean = false;

  constructor(private httpClient: HttpClient, private router: Router, private jwtHelper: JwtHelperService) {
		const token = localStorage.getItem('access_token');
		if(token != null && !this.jwtHelper.isTokenExpired(token)) {
			this.authenticated = true;
		} else {
			this.authenticated = false;
		}
	}

  login(username: string, password: string) {
    return this.httpClient.post<{ access_token: string }>(`${environment.baseUrl}/api/v1/users/authenticate`, { username, password })
      .subscribe(response => {
        localStorage.setItem('access_token', response.access_token);
        this.authenticated = true;
        this.router.navigate(['dashboard']); // Sostituisci con il percorso della tua dashboard
      }, error => {
        console.error('Login failed', error);
        this.authenticated = false;
      });
  } 

  logout() {
    localStorage.removeItem('access_token');
    this.authenticated = false;
    this.router.navigate(['login']); // Sostituisci con il percorso della tua pagina di login
  }

  register(username: string, password: string) {
    return this.httpClient.post(`${environment.baseUrl}/api/v1/users/register`, { username, password })
      .subscribe(response => {
        console.log('Registration successful', response);
        // Puoi aggiungere ulteriori logiche qui, come il login automatico dopo la registrazione
      }, error => {
        console.error('Registration failed', error);
      });
  }

}
