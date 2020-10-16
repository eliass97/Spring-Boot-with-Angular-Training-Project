import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CountryMainComponent } from './country-main/country-main.component';
import { PersonMainComponent } from './person-main/person-main.component';
import { CountryEditComponent } from './country-edit/country-edit.component'
import { PersonEditComponent } from './person-edit/person-edit.component'

const routes: Routes = [
  { path: 'countries', component: CountryMainComponent },
  { path: 'persons', component: PersonMainComponent },
  { path: 'countries/edit/:id', component: CountryEditComponent },
  { path: 'persons/edit/:id', component: PersonEditComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
