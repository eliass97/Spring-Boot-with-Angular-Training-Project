import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CountryMainComponent } from './country-main/country-main.component';
import { PersonMainComponent } from './person-main/person-main.component';

const routes: Routes = [
  { path: 'countries', component: CountryMainComponent },
  { path: 'persons', component: PersonMainComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
