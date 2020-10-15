import { Injectable } from '@angular/core';
import { Country } from './country';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private apiURL = 'http://localhost:8080/country';

  constructor(private http: HttpClient) { }

  /*getCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(this.apiURL).pipe(
      catchError(this.test<Country[]>('getCountries', []))
    );
  }*/

  getCountryById(id: number): Observable<Country> {
    const url = `${this.apiURL}/${id}`;
    return this.http.get<Country>(url);

  }
}