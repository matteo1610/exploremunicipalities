import { Routes } from '@angular/router';
import { MunicipalityListComponent } from './Component/municipality-list/municipality-list.component';
import { MunicipalityDetailComponent } from './Component/municipality-detail/municipality-detail.component';
import { HomeComponent } from './Component/home/home.component';
import { ContactsComponent } from './Component/contacts/contacts.component';
import { LoginComponent } from './Component/auth/login/login.component';
import { SignUpComponent } from './Component/auth/sign-up/sign-up.component';
import { CreatePointOfInterestComponent } from './create-point-of-interest/create-point-of-interest.component';


export const routes: Routes = [
    {path : 'list', component: MunicipalityListComponent},   // Rotta per la lista di municipi
    {path : 'list/:id', component: MunicipalityDetailComponent},    // Rotta per il dettaglio di un singolo municipio
    {path : 'contatti', component: ContactsComponent},    // Rotta per la pagina dei contatti
    {path : 'home', component: HomeComponent},    // Rotta per la homepage
    {path: 'login', component: LoginComponent},    // Rotta per la pagina di login
    {path: 'signup', component: SignUpComponent},    // Rotta per la pagina di registrazione
    {path : 'create-point-of-interest', component: CreatePointOfInterestComponent}, // Rotta per creare un punto di interesse
    {path : '', component: HomeComponent},    // Rotta per la homepage
    { path: '**', redirectTo: '', pathMatch: 'full' }  // Gestione delle rotte non esistenti (404 redirect alla homepage)
];