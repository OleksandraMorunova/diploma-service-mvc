
package com.assistant.registration_service.user.model_data.model;

import com.assistant.registration_service.user.model_data.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.bson.types.ObjectId;
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
    @Schema(description = "Ідентифікатор користувача")
    private String id;

    @Field("name")
    @Schema(description = "Прізвище користувача")
    private String name;

    @Size(min = 4, max = 15)
    @Schema(description = "Телефон користувача")
    private String phone;

    @Indexed @Email
    @Schema(description = "Електронна пошта користувача")
    private String email;

    @Size(min = 8, max = 16, message = "Name must be between 8 and 16 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$")
    @Schema(description = "Пароль користувача")
    private String password;

    @Field("registration_data")
    @Schema(description = "Дата і час реєстрації користувача")
    private String registration_data = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    @Field("roles")
    @Schema(description = "Роль користувача")
   // @Pattern(regexp = "ADMIN|USER", Pattern.CASE_INSENSITIVE)
    private Set<Role> roles;

    @Field("status")
    @Schema(description = "Статус користувача")
    private String status;

    @Field("code")
    @Schema(description = "Одноразовий пароль для входу")
    private String code;

    @Field("token")
    private Map<String, String> token;

    @Field("one_time_code_data")
    @Schema(description = "Дата створення одноразового паролю для входу")
    private String codeData;

    @Field("icon")
    @Schema(description = "Світлина користувача")
    private String icon;

    @Field("firebase_token")
    @Schema(description = "токен ккористувача для надсилання повідомлення")
    private String userTokenFirebase;
}