import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Country } from '../country';
import { CountryService } from '../country.service';

@Component({
  selector: 'app-country-details',
  templateUrl: './country-details.component.html',
  styleUrls: ['./country-details.component.css']
})
export class CountryDetailsComponent implements OnInit {

  country: Country;
  read_only: boolean = true;
  country_edit: Country;

  constructor(private countryService: CountryService, private activatedRoute: ActivatedRoute, private router: Router) {
    this.countryService.getCountryById(Number(this.activatedRoute.snapshot.paramMap.get('id'))).subscribe(retrievedCountry => {
      this.country = retrievedCountry;
      this.country_edit = Object.assign({}, this.country);
    });
    if (history.state.read_only != undefined) {
      this.read_only = history.state.read_only;
    }
  }

  ngOnInit(): void {

  }

  saveEdit(): void {
    this.countryService.putCountryById(this.country.id, this.country_edit).subscribe(retrievedCountry => {
      this.country = retrievedCountry;
      this.read_only = true;
    });
  }

  cancelEdit(): void {
    this.goBack();
  }

  goBack(): void {
    this.router.navigate(['/countries']);
  }

}