package com.assistant.registration_service.user.service.sent_to_email;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSendTwilio {
    @Value("${TWILIO_ACCOUNT_SID}") String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}") String AUTH_TOKEN;
    @Value("${TWILIO_OUTGOING_SMS_NUMBER}") String OUTGOING_SMS_NUMBER;

    public void sendSms(String number, String code){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(
                        new com.twilio.type.PhoneNumber(number),
                        new com.twilio.type.PhoneNumber(OUTGOING_SMS_NUMBER),
                        "[#][Emmo Techie] код підтвердження:" + code + "\nePWjCxibElD")
                .create();
    }
}
