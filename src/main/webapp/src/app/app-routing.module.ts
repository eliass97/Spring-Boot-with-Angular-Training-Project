import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CountryListComponent } from './country-list/country-list.component';
import { PersonListComponent } from './person-list/person-list.component';
import { CountryDetailsComponent } from './country-details/country-details.component'
import { PersonDetailsComponent } from './person-details/person-details.component'

const routes: Routes = [
  { path: 'countries', component: CountryListComponent },
  { path: 'persons', component: PersonListComponent },
  { path: 'countries/:id', component: CountryDetailsComponent },
  { path: 'persons/:id', component: PersonDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }