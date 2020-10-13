package com.example.demo;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.DemoException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;
import com.example.demo.service.CountryService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceTests {

    @Autowired
    private CountryService countryService;

    @MockBean
    private CountryRepository countryRepositoryMock;

    private final int pathId = 123;
    private final int pathIdInvalid = 321;
    private final Timestamp timestampBefore = Timestamp.valueOf("2020-01-01 00:00:00");
    private final Timestamp timestampAfter = Timestamp.valueOf("2021-01-01 00:00:00");
    private final Timestamp timestampInvalid = Timestamp.valueOf("2019-01-01 00:00:00");
    private Country updatedCountry;
    private Country countryRetrievedByDatabase;
    private Country countryAfterSave;

    @Before
    public void setUp() {
        updatedCountry = new Country(123, "ISO_NEW", null, null, timestampBefore);
        countryRetrievedByDatabase = new Country(123, "ISO_OLD", null, null, timestampBefore);
        countryAfterSave = new Country(updatedCountry.getId(), updatedCountry.getIso(), updatedCountry.getDescription(),
                updatedCountry.getPrefix(), timestampAfter);
    }

    @Test
    public void findByIdWhenItDoesNotExist() {
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.empty());

        try {
            countryService.findById(pathId);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void findByIdWhenHappyEnding() throws DemoException {
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.of(countryRetrievedByDatabase));

        Country result = countryService.findById(pathId);
        Assert.assertEquals(result, countryRetrievedByDatabase);

        verify(countryRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void createWhenIsoExistsInDatabase() {
        countryRetrievedByDatabase.setIso(updatedCountry.getIso());

        when(countryRepositoryMock.findByIso(updatedCountry.getIso())).thenReturn(Optional.of(countryRetrievedByDatabase));

        try {
            countryService.create(updatedCountry);
        } catch (BadRequestException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock).findByIso(updatedCountry.getIso());
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void createWhenHappyEnding() throws DemoException {
        when(countryRepositoryMock.findByIso(updatedCountry.getIso())).thenReturn(Optional.empty());
        when(countryRepositoryMock.save(any(Country.class))).thenReturn(countryAfterSave);

        Country result = countryService.create(updatedCountry);
        Assert.assertEquals(result, countryAfterSave);

        verify(countryRepositoryMock).findByIso(updatedCountry.getIso());
        verify(countryRepositoryMock).save(any(Country.class));
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void updateWhenPathIdDifferentThanBodyIdThrowBadRequestException() {
        try {
            countryService.update(pathIdInvalid, updatedCountry);
        } catch (BadRequestException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verifyNoInteractions(countryRepositoryMock);
    }

    @Test
    public void updateWhenCountryIsoAlreadyExistsInDatabase() {
        countryRetrievedByDatabase.setId(pathIdInvalid);
        countryRetrievedByDatabase.setIso(updatedCountry.getIso());

        when(countryRepositoryMock.findByIso(updatedCountry.getIso())).thenReturn(Optional.of(countryRetrievedByDatabase));

        try {
            countryService.update(pathId, updatedCountry);
        } catch (BadRequestException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock, times(2)).findByIso(updatedCountry.getIso());
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void updateWhenPathIdDoesNotExistInDatabase() {
        when(countryRepositoryMock.findByIso(updatedCountry.getIso())).thenReturn(Optional.of(countryRetrievedByDatabase));
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.empty());

        try {
            countryService.update(pathId, updatedCountry);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock, times(2)).findByIso(updatedCountry.getIso());
        verify(countryRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void updateWhenLastUpdateDateIsNullThrowBadRequestException() {
        updatedCountry.setLastUpdateDate(null);

        when(countryRepositoryMock.findByIso(updatedCountry.getIso())).thenReturn(Optional.of(countryRetrievedByDatabase));
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.of(countryRetrievedByDatabase));

        try {
            countryService.update(pathId, updatedCountry);
        } catch (BadRequestException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock, times(2)).findByIso(updatedCountry.getIso());
        verify(countryRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void updateWhenLastUpdateDateInvalidThrowConflictException() {
        updatedCountry.setLastUpdateDate(timestampInvalid);

        when(countryRepositoryMock.findByIso(updatedCountry.getIso())).thenReturn(Optional.of(countryRetrievedByDatabase));
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.of(countryRetrievedByDatabase));

        try {
            countryService.update(pathId, updatedCountry);
        } catch (ConflictException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock, times(2)).findByIso(updatedCountry.getIso());
        verify(countryRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void updateWhenHappyEnding() throws DemoException {
        when(countryRepositoryMock.findByIso(updatedCountry.getIso())).thenReturn(Optional.of(countryRetrievedByDatabase));
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.of(countryRetrievedByDatabase));
        when(countryRepositoryMock.save(any(Country.class))).thenReturn(countryAfterSave);

        Country result = countryService.update(pathId, updatedCountry);
        Assert.assertEquals(result, countryAfterSave);

        verify(countryRepositoryMock, times(2)).findByIso(updatedCountry.getIso());
        verify(countryRepositoryMock).findById(pathId);
        verify(countryRepositoryMock).save(any(Country.class));
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void deleteWhenPathIdDoesNotExistInDatabase() {
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.empty());

        try {
            countryService.delete(pathId);
        } catch (NotFoundException ex) {
            Assert.assertTrue(true);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock).findById(pathId);
        verifyNoMoreInteractions(countryRepositoryMock);
    }

    @Test
    public void deleteWhenHappyEnding() {
        when(countryRepositoryMock.findById(pathId)).thenReturn(Optional.of(countryRetrievedByDatabase));

        try {
            countryService.delete(pathId);
        } catch (DemoException ex) {
            Assert.fail();
        }

        verify(countryRepositoryMock).findById(pathId);
        verify(countryRepositoryMock).deleteById(pathId);
        verifyNoMoreInteractions(countryRepositoryMock);
    }
}