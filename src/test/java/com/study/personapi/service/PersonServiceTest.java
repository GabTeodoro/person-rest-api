package com.study.personapi.service;

import com.study.personapi.dto.MessageResponseDTO;
import com.study.personapi.dto.request.PersonDTO;
import com.study.personapi.entity.Person;
import com.study.personapi.exception.PersonNotFoundException;
import com.study.personapi.mapper.PersonMapper;
import com.study.personapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.study.personapi.utils.PersonUtils.createFakeDTO;
import static com.study.personapi.utils.PersonUtils.createFakeEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOThenReturnSavedMessage() {

        PersonDTO personDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();

        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);
        when(personMapper.toModel(personDTO)).thenReturn(expectedSavedPerson);

        MessageResponseDTO successMessage = personService.createPerson(personDTO);

        assertEquals("Created person with ID 1", successMessage.getMessage());
    }

    @Test
    void testGivenValidPersonIdThenReturnThisPerson() throws PersonNotFoundException {

        PersonDTO expectedPersonDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();
        expectedPersonDTO.setId(expectedSavedPerson.getId());

        when(personRepository.findById(expectedSavedPerson.getId())).thenReturn(Optional.of(expectedSavedPerson));
        when(personMapper.toDTO(expectedSavedPerson)).thenReturn(expectedPersonDTO);
        PersonDTO personDTO = personService.findByid(expectedSavedPerson.getId());


        assertEquals(expectedPersonDTO, personDTO);
        assertEquals(expectedSavedPerson.getId(), personDTO.getId());
        assertEquals(expectedSavedPerson.getFirstName(), personDTO.getFirstName());
    }

    @Test
    void testGivenInvalidPersonIdThenThrowException() {
        var id = 1L;

        when(personRepository.findById(id)).thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class,
                () -> personService.findByid(id));
    }

    @Test
    void testGivenNoDataThenReturnAllPeopleRegistered() {

        List<Person> expectedRegisteredPeople = Collections.singletonList(createFakeEntity());
        PersonDTO personDTO = createFakeDTO();

        when(personRepository.findAll()).thenReturn(expectedRegisteredPeople);
        when(personMapper.toDTO(any(Person.class))).thenReturn(personDTO);

        List<PersonDTO> expectedRegisteredDTOList = personService.listAll();

        assertFalse(expectedRegisteredDTOList.isEmpty());
        assertEquals(expectedRegisteredDTOList.get(0).getId(), personDTO.getId());

    }

    @Test
    void testGivenValidPersonIdAndUpdateInfoThenReturnSuccesOnUpdate() throws PersonNotFoundException {

        var updatedPersonId = 2L;

        PersonDTO updatedPersonDTORequest = createFakeDTO();
        updatedPersonDTORequest.setId(updatedPersonId);
        updatedPersonDTORequest.setLastName("Updated");

        Person expectedPersonToUpdate = createFakeEntity();
        expectedPersonToUpdate.setId(updatedPersonId);

        Person expectedPersonUpdated = createFakeEntity();
        expectedPersonUpdated.setId(updatedPersonId);
        expectedPersonUpdated.setLastName(updatedPersonDTORequest.getLastName());

        when(personRepository.findById(updatedPersonId)).thenReturn(Optional.of(expectedPersonUpdated));
        when(personMapper.toModel(updatedPersonDTORequest)).thenReturn(expectedPersonUpdated);
        when(personRepository.save(any(Person.class))).thenReturn(expectedPersonUpdated);

        MessageResponseDTO successMessage = personService.updateById(updatedPersonId, updatedPersonDTORequest);
        assertEquals("Updated person with ID 2", successMessage.getMessage());
    }

    @Test
    void testGivenInvalidPersonIdAndUpdateInfoThenThrowExceptionOnUpdate() throws PersonNotFoundException {
        var invalidPersonId = 1L;

        PersonDTO updatedPersonDTORequest = createFakeDTO();
        updatedPersonDTORequest.setId(invalidPersonId);
        updatedPersonDTORequest.setLastName("Updated");

        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class,
                () -> personService.updateById(invalidPersonId, updatedPersonDTORequest));
    }

    @Test
    void testGivenValidPersonIdThenReturnSuccesOnDelete() throws PersonNotFoundException {

        var deletedPersonid = 1L;
        Person expectedPersonDelete = createFakeEntity();

        when(personRepository.findById(deletedPersonid))
                .thenReturn(Optional.of(expectedPersonDelete));
        personService.delete(deletedPersonid);

        verify(personRepository, times(1)).deleteById(deletedPersonid);

    }

    @Test
    void testGivenInvalidPersonIdThenReturnSuccessOnDelete() throws PersonNotFoundException {

        var invalidPersonId = 1L;

        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class,
                () -> personService.delete(invalidPersonId));

    }
}