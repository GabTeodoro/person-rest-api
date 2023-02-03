package com.study.personapi.service;

import com.study.personapi.dto.MessageResponseDTO;
import com.study.personapi.dto.request.PersonDTO;
import com.study.personapi.entity.Person;
import com.study.personapi.exception.PersonNotFoundException;
import com.study.personapi.mapper.PersonMapper;
import com.study.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Autowired
    public PersonService(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person person = mapper.toModel(personDTO);
        Person savedPerson = repository.save(person);
        return createMessageResponse("Created person with ID ", savedPerson.getId());
    }

    public List<PersonDTO> listAll() {

        List<Person> people = repository.findAll();
        return people.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findByid(Long id) throws PersonNotFoundException {

        Person person = verifyIfExists(id);
        return mapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {

        verifyIfExists(id);
        repository.deleteById(id);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {

        verifyIfExists(id);
        Person person = mapper.toModel(personDTO);
        Person updatedPerson = repository.save(person);
        return createMessageResponse("Updated person with ID ", updatedPerson.getId());
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new PersonNotFoundException(id));
    }

    private static MessageResponseDTO createMessageResponse(String message, Long id) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
