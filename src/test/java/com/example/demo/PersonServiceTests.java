package com.example.demo;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Country;
import com.example.demo.model.Person;
import com.example.demo.model.PersonDTO;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.CountryService;
import com.example.demo.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTests {

    @Autowired
    private PersonService personService;

    @MockBean
    private CountryService countryServiceMock;

    @MockBean
    private PersonRepository personRepositoryMock;

    private final int pathId = 123;
    private final int pathIdInvalid = 321;
    private final Timestamp timestampBefore = Timestamp.valueOf("2020-01-01 00:00:00");
    private final Timestamp timestampAfter = Timestamp.valueOf("2021-01-01 00:00:00");
    private final Timestamp timestampInvalid = Timestamp.valueOf("2019-01-01 00:00:00");
    private PersonDTO updatedPersonDTO;
    private Person personRetrievedByDatabase;
    private Person personAfterSave;
    private Country countryOfBirthOld;
    private Country countryOfResidenceOld;
    private Country countryOfBirthNew;
    private Country countryOfResidenceNew;

    @Before
    public void setUp() {
        countryOfBirthOld = new Country(11, "B_TEST_OLD", null, null, null);
        countryOfResidenceOld = new Country(22, "R_TEST_OLD", null, null, null);
        countryOfBirthNew = new Country(33, "B_TEST_NEW", null, null, null);
        countryOfResidenceNew = new Country(44, "R_TEST_NEW", null, null, null);
        updatedPersonDTO = new PersonDTO(pathId, null, null, null, countryOfBirthNew.getIso(),
                countryOfResidenceNew.getIso(), null, null, timestampBefore);
        personRetrievedByDatabase = new Person(pathId, null, null, null, countryOfBirthOld,
                countryOfResidenceOld, null, null, timestampBefore);
        personAfterSave = new Person(updatedPersonDTO.getId(), updatedPersonDTO.getFullName(),
                updatedPersonDTO.getDateOfBirth(), updatedPersonDTO.getSex(), countryOfBirthNew, countryOfResidenceNew,
                updatedPersonDTO.getTelephone(), updatedPersonDTO.getEmail(), timestampAfter);
    }

    @Test
    public void createWhenCountryOfBirthDoesNotExist() throws DemoException {
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfBirth())).thenThrow(new NotFoundException());

        try {
            personService.create(updatedPersonDTO);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfBirth());
        verifyNoInteractions(personRepositoryMock);
        verifyNoMoreInteractions(countryServiceMock);
    }

    @Test
    public void createWhenCountryOfResidenceDoesNotExist() throws DemoException {
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfBirth())).thenReturn(countryOfBirthNew);
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfResidence())).thenThrow(new NotFoundException());

        try {
            personService.create(updatedPersonDTO);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfBirth());
        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfResidence());
        verifyNoInteractions(personRepositoryMock);
        verifyNoMoreInteractions(countryServiceMock);
    }

    @Test
    public void createWhenHappyEnding() throws DemoException {
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfBirth())).thenReturn(countryOfBirthNew);
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfResidence())).thenReturn(countryOfResidenceNew);
        when(personRepositoryMock.save(any(Person.class))).thenReturn(personAfterSave);

        PersonDTO result = personService.create(updatedPersonDTO);
        Assert.assertEquals(result, PersonDTO.mapper(personAfterSave));

        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfBirth());
        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfResidence());
        verify(personRepositoryMock).save(any(Person.class));
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoMoreInteractions(countryServiceMock);
    }

    @Test
    public void updateWhenPathIdDifferentThanBodyIdThrowBadRequestException() {
        try {
            personService.update(pathIdInvalid, updatedPersonDTO);
        } catch (BadRequestException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verifyNoInteractions(countryServiceMock);
        verifyNoInteractions(personRepositoryMock);
    }

    @Test
    public void updateWhenCountryOfBirthOrResidenceIsNullThrowBadRequestException() {
        updatedPersonDTO.setCountryOfBirth(null);

        try {
            personService.update(pathId, updatedPersonDTO);
        } catch (BadRequestException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verifyNoInteractions(countryServiceMock);
        verifyNoInteractions(personRepositoryMock);
    }

    @Test
    public void updateWhenPathIdDoesNotExistInDatabase() {
        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.empty());

        try {
            personService.update(pathId, updatedPersonDTO);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(personRepositoryMock).findById(pathId);
        verifyNoInteractions(countryServiceMock);
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    public void updateWhenLastUpdateDateIsNullThrowBadRequestException() {
        updatedPersonDTO.setLastUpdateDate(null);

        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.of(personRetrievedByDatabase));

        try {
            personService.update(pathId, updatedPersonDTO);
        } catch (BadRequestException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verifyNoInteractions(countryServiceMock);
        verify(personRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    public void updateWhenLastUpdateDateInvalidThrowConflictException() {
        updatedPersonDTO.setLastUpdateDate(timestampInvalid);

        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.of(personRetrievedByDatabase));

        try {
            personService.update(pathId, updatedPersonDTO);
        } catch (ConflictException e) {
            Assert.assertTrue(true);
        } catch (Exception ex) {
            Assert.fail();
        }

        verifyNoInteractions(countryServiceMock);
        verify(personRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    public void updateWhenCountryOfBirthDoesNotExistThrowNotFoundException() throws DemoException {
        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.of(personRetrievedByDatabase));
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfBirth())).thenThrow(new NotFoundException());

        try {
            personService.update(pathId, updatedPersonDTO);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(personRepositoryMock).findById(pathId);
        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfBirth());
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoMoreInteractions(countryServiceMock);
    }

    @Test
    public void updateWhenCountryOfResidenceDoesNotExist() throws DemoException {
        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.of(personRetrievedByDatabase));
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfBirth())).thenReturn(countryOfBirthNew);
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfResidence())).thenThrow(new NotFoundException());

        try {
            personService.update(pathId, updatedPersonDTO);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(personRepositoryMock).findById(pathId);
        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfBirth());
        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfResidence());
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoMoreInteractions(countryServiceMock);
    }

    @Test
    public void updateWhenHappyEnding() throws DemoException {
        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.of(personRetrievedByDatabase));
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfBirth())).thenReturn(countryOfBirthNew);
        when(countryServiceMock.getCountryByIso(updatedPersonDTO.getCountryOfResidence())).thenReturn(countryOfResidenceNew);
        when(personRepositoryMock.save(personRetrievedByDatabase)).thenReturn(personAfterSave);

        PersonDTO result = personService.update(pathId, updatedPersonDTO);
        Assert.assertEquals(result, PersonDTO.mapper(personAfterSave));

        verify(personRepositoryMock).findById(pathId);
        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfBirth());
        verify(countryServiceMock).getCountryByIso(updatedPersonDTO.getCountryOfResidence());
        verify(personRepositoryMock).save(personRetrievedByDatabase);
        verifyNoMoreInteractions(personRepositoryMock);
        verifyNoMoreInteractions(countryServiceMock);
    }

    @Test
    public void deleteWhenPathIdDoesNotExistInDatabase() {
        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.empty());

        try {
            personService.delete(pathId);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(personRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    public void deleteWhenHappyEnding() {
        when(personRepositoryMock.findById(pathId)).thenReturn(Optional.of(personRetrievedByDatabase));

        try {
            personService.delete(pathId);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(personRepositoryMock).findById(pathId);
        verify(personRepositoryMock).deleteById(pathId);
        verifyNoMoreInteractions(personRepositoryMock);
    }
}