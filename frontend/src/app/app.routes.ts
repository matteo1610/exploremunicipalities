import { Routes } from '@angular/router';
import { MunicipalityListComponent } from './Component/municipality-list/municipality-list.component';
import { MunicipalityDetailComponent } from './Component/municipality-detail/municipality-detail.component';

export const routes: Routes = [
    {path : 'list', component: MunicipalityListComponent},   // Rotta per la lista di municipi
    {path : 'list/:id', component: MunicipalityDetailComponent},    // Rotta per il dettaglio di un singolo municipio
];