import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CountryListComponent } from './country-list/country-list.component';
import { PersonListComponent } from './person-list/person-list.component';
import { CountryDetailsComponent } from './country-details/country-details.component'
import { PersonDetailsComponent } from './person-details/person-details.component'
import { PersonNewComponent } from './person-new/person-new.component';
import { CountryNewComponent } from './country-new/country-new.component';

const routes: Routes = [
  { path: 'countries', component: CountryListComponent },
  { path: 'persons', component: PersonListComponent },
  { path: 'countries/:id', component: CountryDetailsComponent },
  { path: 'persons/:id', component: PersonDetailsComponent },
  { path: 'country_new', component: CountryNewComponent },
  { path: 'person_new', component: PersonNewComponent },
  { path: '', redirectTo: '#/web', pathMatch: 'full' },
  { path: '**', redirectTo: '#/web', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }