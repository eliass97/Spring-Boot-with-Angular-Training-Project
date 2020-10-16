import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Person } from '../person';
import { PersonService } from '../person.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-person-main',
  templateUrl: './person-main.component.html',
  styleUrls: ['./person-main.component.css']
})
export class PersonMainComponent implements OnInit {

  input_id: number;
  selectedPerson: Person;

  constructor(private personService: PersonService, private route: ActivatedRoute) { }

  ngOnInit(): void { }

  getPerson(): void {
    this.personService.getPersonById(this.input_id)
      .pipe(catchError(error => { return of(undefined); }))
      .subscribe(person => this.selectedPerson = person);
  }

  deletePerson(): void {
    if (confirm("Are you sure you want to delete it?")) {
      this.personService.deletePersonById(this.selectedPerson.id);
      this.selectedPerson = undefined;
    }
  }
}