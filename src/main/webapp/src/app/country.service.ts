import { Injectable } from '@angular/core';
import { Country } from './country';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private apiURL = '/country';

  constructor(private httpclient: HttpClient) { }

  getCountries(): Observable<Country[]> {
    return this.httpclient.get<Country[]>(this.apiURL);
  }

  getCountryById(id: number): Observable<Country> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.get<Country>(url);
  }

  putCountryById(id: number, country: Country): Observable<Country> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.put<Country>(url, country);
  }

  getCountriesByPage(page: number, pageSize: number): Observable<Country[]> {
    const url = `${this.apiURL}/page?page=${page}&size=${pageSize}`;
    return this.httpclient.get<Country[]>(url);
  }

  deleteCountryById(id: number): void {
    const url = `${this.apiURL}/${id}`;
    this.httpclient.delete(url).subscribe(data => console.log(data));
  }
}