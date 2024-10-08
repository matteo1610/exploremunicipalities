
import { Injectable } from '@angular/core';
import {
	HttpRequest,
	HttpHandler,
	HttpEvent,
	HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable()
export class HeaderInterceptor implements HttpInterceptor {

	// This http interceptor is responsible of adding the correct authentication token
	// to every http request sent to the server
	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		const excludedUrls = [`${environment.baseUrl}/api/v1/users/authenticate`, `${environment.baseUrl}/api/v1/users/authenticate`];
		console.log(request.url)
		if(excludedUrls.some(url => request.url.includes(url))) {
			return next.handle(request);
		}

		const modifiedRequest = request.clone({
			setHeaders: {
				'Content-Type': 'application/json; charset=utf-8',
				'Authorization': 'Bearer ' + localStorage.getItem("access_token")!
			}
		});

		return next.handle(modifiedRequest);
	}
}
