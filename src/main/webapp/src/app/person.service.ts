import { Injectable } from '@angular/core';
import { Person } from './person';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private apiURL = '/api/persons';

  constructor(private httpclient: HttpClient) { }

  getPersonById(id: number): Observable<Person> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.get<Person>(url);

  }

  getPersonsByPage(page: number, pageSize: number): Observable<Person[]> {
    const url = `${this.apiURL}/page?page=${page}&size=${pageSize}`;
    return this.httpclient.get<Person[]>(url);
  }

  getPersonsByPageSortedByField(page: number, pageSize: number, field: string): Observable<Person[]> {
    const url = `${this.apiURL}/page?page=${page}&size=${pageSize}&sort=${field},asc`;
    return this.httpclient.get<Person[]>(url);
  }

  postPerson(person: Person): Observable<Person> {
    return this.httpclient.post<Person>(this.apiURL, person);
  }

  putPersonById(id: number, person: Person): Observable<Person> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.put<Person>(url, person);
  }

  async deletePersonById(id: number) {
    const url = `${this.apiURL}/${id}`;
    this.httpclient.delete(url).subscribe();
  }
}