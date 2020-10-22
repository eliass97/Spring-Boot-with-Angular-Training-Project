import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { Country } from '../country';
import { CountryService } from '../country.service';
import { Person } from '../person';
import { PersonService } from '../person.service';

@Component({
  selector: 'app-person-new',
  templateUrl: './person-new.component.html',
  styleUrls: ['./person-new.component.css']
})
export class PersonNewComponent implements OnInit {

  countries: Country[];
  person: Person = { id: 0, fullName: null, dateOfBirth: null, sex: null, countryOfBirth: null, countryOfResidence: null, telephone: null, email: null, lastUpdateDate: null }

  constructor(private personService: PersonService, private countryService: CountryService, private router: Router, private customAdapter: NgbDateAdapter<Date>) {
    this.countryService.getCountries().subscribe(retrievedCountries => {
      this.countries = retrievedCountries;
    });
  }

  ngOnInit(): void {
  }

  findDescriptionByISO(iso: string) {
    if (this.countries != undefined && iso != undefined) {
      return this.countries.filter(x => x.iso == iso).pop().description;
    }
    return null;
  }

  editCountryOfBirth(iso: string): void {
    this.person.countryOfBirth = iso;
  }

  editCountryOfResidence(iso: string): void {
    this.person.countryOfResidence = iso;
  }

  create(): void {
    this.personService.postPerson(this.person).subscribe(retrievedPerson => {
      this.person = retrievedPerson;
      this.router.navigate(['/persons', this.person.id])
    });
  }

  cancel(): void {
    this.router.navigate(['/persons']);
  }
}