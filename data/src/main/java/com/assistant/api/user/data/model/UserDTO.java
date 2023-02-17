package com.assistant.api.user.data.model;

import com.assistant.api.user.data.enums.Role;
import com.mongodb.BasicDBObject;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Data
public class UserDTO {
    private String userId;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String password;
    private String registrationData = String.valueOf(new Date(System.currentTimeMillis()));
    private Set<Role> role = Collections.singleton(Role.USER);
    private BasicDBObject doc = new BasicDBObject("role", "readWrite").append("db", "user_accounts");
    private String status;

    public UserDTO() {
    }

    public UserDTO(String firstname, String lastname, String phone, String email, String password, String status) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.status = status;
    }
}
