package com.study.personapi.utils;

import com.study.personapi.dto.request.PersonDTO;
import com.study.personapi.entity.Person;

import java.time.LocalDate;
import java.util.Collections;

public class PersonUtils {

    private static final Long PERSON_ID = 1L;
    private static final String FIRST_NAME = "Thomas";
    private static final String LAST_NAME = "Shelby";
    private static final String CPF_NUMBER = "550.825.600-85";
    private static final LocalDate BIRTH_DATE = LocalDate.of(2001,12,02);

    public static PersonDTO createFakeDTO() {
        return PersonDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate("04-04-2010")
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static Person createFakeEntity() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtils.createFakeEntity()))
                .build();
    }

}