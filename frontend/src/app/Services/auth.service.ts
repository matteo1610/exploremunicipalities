import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { UserInfo } from '../Model/user-info';
import { environment } from '../../environments/environment.development';
import { User } from '../Model/user';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
    public authenticated: boolean = false;

  constructor(private httpClient: HttpClient, private router: Router) { }

  public signup(user: User): Observable<any> {
    const payload = {
        email: user.email,
        password: user.password
    };

    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
    });

    return this.httpClient.post(`${environment.baseUrl}/api/v1/users/register`, payload, { headers: headers, observe: 'response' })
        .pipe(
            tap((response: HttpResponse<any>) => {
                this.router.navigate(['/login']);
            }),
            catchError(error => {
                console.error('Registrazione fallita. Errore:', error);
                return throwError(() => error);
            })
        );
}

public login(credentials: { email: string; password: string }): Observable<any> {
    return this.httpClient.post(`${environment.baseUrl}/api/v1/users/authenticate`, credentials, { observe: 'response' })
        .pipe(tap((response: HttpResponse<any>) => {
                const authToken = response.body?.token;
                if (authToken) {
                    this.storeToken(authToken);
                    localStorage.getItem('access_token'); 
                    const userInfo = JSON.stringify(response.body);
                    localStorage.setItem("user-info", userInfo);
                    this.authenticated = true;
                }
            }),
            catchError(error => {
                console.error('Autenticazione fallita. Errore:', error);
                return throwError(() => error);
            })
        );
}

public logout(): void {
    localStorage.removeItem("access_token");
    localStorage.removeItem("user-info")
    this.router.navigate(['/home']);
    this.authenticated = false;
}


public getUserInfo() : UserInfo | null {
    const userInfo = localStorage.getItem("user-info");
    return JSON.parse(userInfo!);
}


private storeToken(token: string): void {
    localStorage.setItem('access_token', token);
}

private extractAuthToken(headers: HttpHeaders): string | null {
    const authHeader = headers.get('Authorization');
    return authHeader ? authHeader.split(' ')[1] : null;
  }
}
