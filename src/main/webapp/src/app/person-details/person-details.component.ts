import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Country } from '../country';
import { CountryService } from '../country.service';
import { Person } from '../person';
import { PersonService } from '../person.service';

@Component({
  selector: 'app-person-details',
  templateUrl: './person-details.component.html',
  styleUrls: ['./person-details.component.css']
})
export class PersonDetailsComponent implements OnInit {

  person_view: Person;
  read_only: boolean;
  person_edit: Person;
  countries: Country[] = [];
  person_viewBirthCountryDescription: string;
  person_viewResidenceCountryDescription: string;
  person_editBirthCountryDescription: string;
  person_editResidenceCountryDescription: string;
  date_picker: NgbDateStruct;

  constructor(private personService: PersonService, private countryService: CountryService, private activatedRoute: ActivatedRoute, private router: Router) {
    personService.getPersonById(history.state.id).subscribe(retrievedPerson => {
      this.person_view = retrievedPerson
      this.person_view.dateOfBirth = new Date(this.person_view.dateOfBirth);
      //REMEMBER: IN DATE MONTHS START COUNTING FROM 0
      this.person_edit = Object.assign({}, this.person_view);
      this.date_picker = new NgbDate(this.person_view.dateOfBirth.getUTCFullYear(), this.person_view.dateOfBirth.getUTCMonth() + 1, this.person_view.dateOfBirth.getUTCDate());
    });
    this.countryService.getCountries().subscribe(retrievedCountries => {
      this.countries = retrievedCountries;
      this.person_viewBirthCountryDescription = this.findDescriptionByISO(this.person_view.countryOfBirth);
      this.person_viewResidenceCountryDescription = this.findDescriptionByISO(this.person_view.countryOfResidence);
      this.person_editBirthCountryDescription = this.findDescriptionByISO(this.person_edit.countryOfBirth);
      this.person_editResidenceCountryDescription = this.findDescriptionByISO(this.person_edit.countryOfResidence);
    });
    this.read_only = history.state.read_only;
  }

  ngOnInit(): void {

  }

  showDateOfBirth(): string {
    return this.person_view.dateOfBirth.toLocaleDateString();
  }

  findDescriptionByISO(iso: string) {
    return this.countries.filter(x => x.iso == iso).pop().description;
  }

  editCountryOfBirthISO(iso: string): void {
    this.person_edit.countryOfBirth = iso;
    this.person_editBirthCountryDescription = this.findDescriptionByISO(iso);
  }

  editCountryOfResidenceISO(iso: string): void {
    this.person_edit.countryOfResidence = iso;
    this.person_editResidenceCountryDescription = this.findDescriptionByISO(iso);
  }

  saveEdit(): void {
    this.person_edit.dateOfBirth = new Date(this.date_picker.year, this.date_picker.month - 1, this.date_picker.day + 1);
    this.personService.putPersonById(this.person_view.id, this.person_edit).subscribe(retrievedPerson => {
      this.person_view = retrievedPerson;
      this.person_view.dateOfBirth = new Date(this.person_view.dateOfBirth);
      this.date_picker = new NgbDate(this.person_view.dateOfBirth.getUTCFullYear(), this.person_view.dateOfBirth.getUTCMonth() + 1, this.person_view.dateOfBirth.getUTCDate());
      this.person_viewBirthCountryDescription = this.findDescriptionByISO(this.person_view.countryOfBirth);
      this.person_viewResidenceCountryDescription = this.findDescriptionByISO(this.person_view.countryOfResidence);
      this.read_only = true;
    });
  }

  cancelEdit(): void {
    this.goBack();
  }

  goBack(): void {
    this.router.navigate(['/persons']);
  }
}