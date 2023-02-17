
package com.assistant.api.user.data.model;

import com.assistant.api.user.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Document(collection = "user_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Schema(description = "Ідентифікатор користувача")
    private String userId;

    @Field("first_name")
    @Schema(description = "Ім'я користувача")
    private String firstname;

    @Field("last_name")
    @Schema(description = "Прізвище користувача")
    private String lastname;

    @Size(max = 10)
    @Schema(description = "Телефон користувача")
    private String phone;

    @Indexed @Email
    @Schema(description = "Електронна пошта користувача")
    private String email;

    @Schema(description = "Пароль користувача")
    private String password;

    @Field("registration_data")
    @Schema(description = "Дата і час реєстрації користувача")
    private String registrationData = String.valueOf(new Date(System.currentTimeMillis()));

    @Field("roles")
    @Schema(description = "Роль користувача")
    private Set<Role> role = Collections.singleton(Role.ADMIN);

    @Field("status")
    @Schema(description = "Статус користувача")
    private String status;

}
