import { Component, OnInit } from '@angular/core';
import { Person } from '../person';
import { PersonService } from '../person.service';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {

  page: number = 1;
  pageSize: number = 10;
  persons: Person[] = [];
  totalPersons: number;

  constructor(private personService: PersonService) { }

  ngOnInit(): void {
    this.refreshPage();
  }

  deletePerson(id: number) {
    if (confirm("Are you sure you want to delete it?")) {
      this.personService.deletePersonById(id).then(res => this.refreshPage());
    }
  }

  refreshPage(): void {
    this.personService.getPersonsByPage(this.page - 1, this.pageSize).subscribe(retrievedPersons => {
      this.persons = retrievedPersons['content'];
      this.totalPersons = retrievedPersons['totalElements'];
    });
  }

  changePageSize(size: number): void {
    this.pageSize = size;
    this.refreshPage();
  }

}