import { Injectable } from '@angular/core';
import { Person } from './person';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private apiURL = 'http://localhost:8080/person';

  constructor(private http: HttpClient) { }

  getPersonById(id: number): Observable<Person> {
    const url = `${this.apiURL}/${id}`;
    return this.http.get<Person>(url);

  }
}
