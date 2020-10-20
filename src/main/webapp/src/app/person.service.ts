import { Injectable } from '@angular/core';
import { Person } from './person';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private apiURL = '/person';

  constructor(private httpclient: HttpClient) { }

  getPersonById(id: number): Observable<Person> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.get<Person>(url);

  }

  putPersonById(id: number, person: Person): Observable<Person> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.put<Person>(url, person);
  }

  getPersonsByPage(page: number, pageSize: number): Observable<Person[]> {
    const url = `${this.apiURL}/page?page=${page}&size=${pageSize}`;
    return this.httpclient.get<Person[]>(url);
  }

  deletePersonById(id: number): void {
    const url = `${this.apiURL}/${id}`;
    this.httpclient.delete(url).subscribe(data => console.log(data));
  }
}