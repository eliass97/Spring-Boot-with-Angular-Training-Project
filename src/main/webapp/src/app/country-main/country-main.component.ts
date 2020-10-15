import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Country } from '../country';
import { CountryService } from '../country.service';

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

  getCountryById(): void {
    this.countryService.getCountryById(this.input_id)
      .subscribe(country => this.selectedCountry = country);
  }
}
