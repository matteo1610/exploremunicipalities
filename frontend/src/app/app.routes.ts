import { Routes } from '@angular/router';
import { MunicipalityListComponent } from './Component/municipality-list/municipality-list.component';
import { MunicipalityDetailComponent } from './Component/municipality-detail/municipality-detail.component';
import { HomeComponent } from './home/home.component';


export const routes: Routes = [
    {path : 'list', component: MunicipalityListComponent},   // Rotta per la lista di municipi
    {path : 'list/:id', component: MunicipalityDetailComponent},    // Rotta per il dettaglio di un singolo municipio
    {path : '', component: HomeComponent},    // Rotta per la homepage
    { path: '**', redirectTo: '', pathMatch: 'full' }  // Gestione delle rotte non esistenti (404 redirect alla homepage)
];