package com.study.personapi.service;

import com.study.personapi.dto.MessageResponseDTO;
import com.study.personapi.dto.request.PersonDTO;
import com.study.personapi.entity.Person;
import com.study.personapi.mapper.PersonMapper;
import com.study.personapi.repository.PersonRepository;
import com.study.personapi.utils.PersonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.study.personapi.utils.PersonUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

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

        MessageResponseDTO expectedSuccessMessage = createExpectedMessageResponse(expectedSavedPerson.getId());
        MessageResponseDTO successMessage = personService.createPerson(personDTO);

        assertEquals(expectedSuccessMessage, successMessage);
    }

    private static MessageResponseDTO createExpectedMessageResponse(Long id) {
        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + id)
                .build();
    }
}