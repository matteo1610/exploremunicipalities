import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User } from './Model/user';
import { environment } from '../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

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

}