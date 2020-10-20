import { NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

export interface Person {
    id: number;
    fullName: string;
    dateOfBirth: Date;
    sex: string;
    countryOfBirth: string;
    countryOfResidence: string;
    email: string;
    telephone: string;
    lastUpdateDate: Date;
}