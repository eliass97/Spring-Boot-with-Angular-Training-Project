import { Injectable } from '@angular/core';
import { Country } from './country';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private apiURL = 'http://localhost:8080/country';

  constructor(private httpclient: HttpClient) { }

  getCountryById(id: number): Observable<Country> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.get<Country>(url);

  }

  public putCountryById(country: Country): Observable<Country> {
    const url = `${this.apiURL}/${country.id}`;
    return this.httpclient.put<Country>(url, country);
  }

  deleteCountryById(id: number): void {
    const url = `${this.apiURL}/${id}`;
    this.httpclient.delete(url).subscribe(data => console.log(data));
  }
}