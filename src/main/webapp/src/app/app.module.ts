import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
//import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { CountryMainComponent } from './country-main/country-main.component';
import { PersonMainComponent } from './person-main/person-main.component';
import { CountryEditComponent } from './country-edit/country-edit.component';
import { PersonEditComponent } from './person-edit/person-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    CountryMainComponent,
    PersonMainComponent,
    CountryEditComponent,
    PersonEditComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    //NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
