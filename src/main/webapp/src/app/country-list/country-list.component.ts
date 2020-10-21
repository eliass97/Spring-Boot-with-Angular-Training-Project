import { Component, OnInit } from '@angular/core';
import { Country } from '../country';
import { CountryService } from '../country.service';

@Component({
  selector: 'app-country-list',
  templateUrl: './country-list.component.html',
  styleUrls: ['./country-list.component.css']
})
export class CountryListComponent implements OnInit {

  page: number = 1;
  pageSize: number = 10;
  countries: Country[] = [];
  totalCountries: number;

  constructor(private countryService: CountryService) { }

  ngOnInit(): void {
    this.refreshPage();
  }

  deleteCountry(id: number): void {
    if (confirm("Are you sure you want to delete it?")) {
      this.countryService.deleteCountryById(id).then(res => this.refreshPage());
    }
  }

  refreshPage(): void {
    this.countryService.getCountriesByPage(this.page - 1, this.pageSize).subscribe(retrievedCountries => {
      this.countries = retrievedCountries['content'];
      this.totalCountries = retrievedCountries['totalElements'];
    });
  }

  changePageSize(size: number): void {
    this.pageSize = size;
    this.refreshPage();
  }

}