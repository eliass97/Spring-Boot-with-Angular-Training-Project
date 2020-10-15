import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Person } from '../person';
import { PersonService } from '../person.service';

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

  getPersonById(): void {
    this.personService.getPersonById(this.input_id)
      .subscribe(person => this.selectedPerson = person);
  }

}
