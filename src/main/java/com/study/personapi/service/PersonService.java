package com.study.personapi.service;

import com.study.personapi.dto.MessageResponseDTO;
import com.study.personapi.entity.Person;
import com.study.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }


    public MessageResponseDTO createPerson(Person person) {
        Person savedPerson = repository.save(person);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + savedPerson.getId())
                .build();
    }
}
