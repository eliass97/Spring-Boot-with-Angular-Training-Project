import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
//import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { CountryMainComponent } from './country-main/country-main.component';
import { PersonMainComponent } from './person-main/person-main.component';

@NgModule({
  declarations: [
    AppComponent,
    CountryMainComponent,
    PersonMainComponent,
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
