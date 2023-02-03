package com.study.personapi.utils;

import com.study.personapi.dto.request.PhoneDTO;
import com.study.personapi.entity.Phone;
import com.study.personapi.enums.PhoneType;

public class PhoneUtils {

    private static final Long PHONE_ID = 1L;
    private static final String PHONE_NUMBER = "1198745-4571";
    private static final PhoneType PHONE_TYPE = PhoneType.MOBILE;

    public static PhoneDTO createFakeDTO() {
        return PhoneDTO.builder()
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

    public static Phone createFakeEntity() {
        return Phone.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }
}
