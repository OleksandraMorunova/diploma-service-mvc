
package com.assistant.registration_service.user.model_data.model;

import com.assistant.registration_service.user.model_data.enums.Role;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */
@Document(collection = "user_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Size(min = 4, max = 15)
    private String phone;

    @Indexed @Email
    private String email;

    @Size(min = 8, max = 16, message = "Name must be between 8 and 16 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$")
    private String password;

    @Field("registration_data")
    private String registration_data = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    @Field("roles")
   // @Pattern(regexp = "ADMIN|USER", Pattern.CASE_INSENSITIVE)
    private Set<Role> roles;

    @Field("status")
    private String status;

    @Field("code")
    private String code;

    @Field("token")
    private Map<String, String> token;

    @Field("one_time_code_data")
    private String codeData;

    @Field("icon")
    private String icon;

    @Field("firebase_token")
    private String userTokenFirebase;
}