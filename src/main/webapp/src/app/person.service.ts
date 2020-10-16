import { Injectable } from '@angular/core';
import { Person } from './person';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private apiURL = 'http://localhost:8080/person';

  constructor(private httpclient: HttpClient) { }

  getPersonById(id: number): Observable<Person> {
    const url = `${this.apiURL}/${id}`;
    return this.httpclient.get<Person>(url);

  }

  public putPersonById(person: Person): Observable<Person> {
    const url = `${this.apiURL}/${person.id}`;
    return this.httpclient.put<Person>(url, person);
  }

  deletePersonById(id: number): void {
    const url = `${this.apiURL}/${id}`;
    this.httpclient.delete(url).subscribe(data => console.log(data));;
  }
}
