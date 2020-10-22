import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Country } from '../country';
import { CountryService } from '../country.service';

@Component({
  selector: 'app-country-new',
  templateUrl: './country-new.component.html',
  styleUrls: ['./country-new.component.css']
})
export class CountryNewComponent implements OnInit {

  country: Country = { id: 0, iso: null, description: null, prefix: null, lastUpdateDate: null }

  constructor(private countryService: CountryService, private router: Router) { }

  ngOnInit(): void {
  }

  create(): void {
    this.countryService.postCountry(this.country).subscribe(retrievedCountry => {
      this.country = retrievedCountry;
      this.router.navigate(['/countries', this.country.id])
    });
  }

  cancel(): void {
    this.router.navigate(['/countries']);
  }
}