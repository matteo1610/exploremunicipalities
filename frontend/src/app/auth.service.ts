import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { environment } from '../environments/environment'; // Assicurati che il percorso sia corretto
import { User } from './Model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private router: Router) { }

  public signup(user: User): Observable<any> {
    return this.httpClient.post(`${environment.baseUrl}/auth/signup`, user, { observe: 'response' })
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