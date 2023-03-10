package com.study.personapi.controller;

import com.study.personapi.dto.MessageResponseDTO;
import com.study.personapi.dto.request.PersonDTO;
import com.study.personapi.exception.PersonNotFoundException;
import com.study.personapi.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private PersonService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO) {
        return service.createPerson(personDTO);
    }

    @GetMapping
    private List<PersonDTO> listAll() {
       return service.listAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private PersonDTO findById(@PathVariable Long id) throws PersonNotFoundException {
        return service.findByid(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteById(@PathVariable Long id) throws PersonNotFoundException {
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private MessageResponseDTO updateByid(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO)
            throws PersonNotFoundException {
        return service.updateById(id, personDTO);
    }
}
