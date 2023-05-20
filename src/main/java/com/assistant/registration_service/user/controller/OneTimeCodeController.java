package com.assistant.registration_service.user.controller;

import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.service.one_time_password.OneTimeCodeService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("otc")
@RestController
@Validated
@RequiredArgsConstructor
public class OneTimeCodeController {
    private final OneTimeCodeService service;

    @PutMapping("/generated/code")
   public ResponseEntity<?> sentToPhoneNumberAndDataBase(@Valid @RequestBody User phoneNumber) {
        return new ResponseEntity<>(service.sentToPhoneNumberAndDataBase(phoneNumber), HttpStatus.OK);
    }

    @DeleteMapping("/get/code/{code}")
    public ResponseEntity<?> getAndDeleteCode(@Valid @PathVariable("code") @Pattern(regexp = "[0-9]+")
                                                  @NotBlank(message = "Code may not be empty")
                                                  @Size(min = 5, max = 5, message = "Name must be 5 characters long") String code) {
        service.deleteCode(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sms")
    public ResponseEntity<User> sentToEmailAndDataBase(@Valid @RequestBody User emailAndPhone) {
        return new ResponseEntity<>(service.sentToEmailAndDataBase(emailAndPhone), HttpStatus.OK);
    }
}
