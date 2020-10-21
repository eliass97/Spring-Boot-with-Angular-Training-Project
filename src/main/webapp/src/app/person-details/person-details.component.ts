import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Country } from '../country';
import { CountryService } from '../country.service';
import { Person } from '../person';
import { PersonService } from '../person.service';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-person-details',
  templateUrl: './person-details.component.html',
  styleUrls: ['./person-details.component.css'],
  providers: []
})
export class PersonDetailsComponent implements OnInit {

  person_view: Person;
  read_only: boolean = true;
  person_edit: Person;
  countries: Country[];

  constructor(private personService: PersonService, private countryService: CountryService, private activatedRoute: ActivatedRoute, private router: Router, private customAdapter: NgbDateAdapter<Date>) {
    personService.getPersonById(Number(this.activatedRoute.snapshot.paramMap.get('id'))).subscribe(retrievedPerson => {
      this.person_view = retrievedPerson
      this.setDates();
      this.person_edit = Object.assign({}, this.person_view);
    });
    this.countryService.getCountries().subscribe(retrievedCountries => {
      this.countries = retrievedCountries;
    });
    if (history.state.read_only != undefined) {
      this.read_only = history.state.read_only;
    }
  }

  ngOnInit(): void {

  }

  //This method is required only because most dates of birth in the database are undefined
  setDates() {
    if (this.person_view.dateOfBirth == undefined) {
      this.person_view.dateOfBirth = null;
    } else {
      this.person_view.dateOfBirth = new Date(this.person_view.dateOfBirth);
    }
  }

  showDateInFormatting(date: Date): string {
    if (date != null) {
      return date.toLocaleDateString('en-GB', { timeZone: 'UTC' });
    } else {
      return "A/N";
    }
  }

  findDescriptionByISO(iso: string) {
    if (this.countries != undefined) {
      return this.countries.filter(x => x.iso == iso).pop().description;
    }
    return null;
  }

  editCountryOfBirth(iso: string): void {
    this.person_edit.countryOfBirth = iso;
  }

  editCountryOfResidence(iso: string): void {
    this.person_edit.countryOfResidence = iso;
  }

  saveButton(): void {
    this.personService.putPersonById(this.person_view.id, this.person_edit).subscribe(retrievedPerson => {
      this.person_view = retrievedPerson;
      this.setDates();
    });
    this.read_only = true;
  }

  cancelButton(): void {
    this.backButton();
  }

  backButton(): void {
    this.router.navigate(['/persons']);
  }
}