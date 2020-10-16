import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Country } from '../country';
import { CountryService } from '../country.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-country-main',
  templateUrl: './country-main.component.html',
  styleUrls: ['./country-main.component.css']
})
export class CountryMainComponent implements OnInit {

  input_id: number;
  selectedCountry: Country;

  constructor(private countryService: CountryService, private route: ActivatedRoute) { }

  ngOnInit(): void { }

  getCountry(): void {
    this.countryService.getCountryById(this.input_id)
      .pipe(catchError(error => { return of(undefined); }))
      .subscribe(country => this.selectedCountry = country);
  }

  deleteCountry(): void {
    if (confirm("Are you sure you want to delete it?")) {
      this.countryService.deleteCountryById(this.selectedCountry.id);
      this.selectedCountry = undefined;
    }
  }
}