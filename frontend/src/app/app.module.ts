import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { JwtModule, JwtHelperService } from '@auth0/angular-jwt'; // Importa JwtModule e JwtHelperService
import { LoginComponent } from './Component/auth/login/login.component';
import { SignupComponent } from './Component/auth/sign-up/sign-up.component';

@NgModule({
  declarations: [

  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    JwtModule.forRoot({}) // Configura JwtModule se necessario
  ],
  providers: [JwtHelperService], // Aggiungi JwtHelperService ai provider
})
export class AppModule { }