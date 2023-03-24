package com.assistant.registration_service.user.service.one_time_password;

import java.io.Serializable;

public interface OneTimeCodeServiceInterface<T, ID extends Serializable> {
    T sentToPhoneNumberAndDataBase(T phoneNumber);
    void deleteCode(ID code);
    T sentToEmailAndDataBase(T user);
}