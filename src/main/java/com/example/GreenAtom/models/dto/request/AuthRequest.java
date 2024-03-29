package com.example.GreenAtom.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Tribushko Danil
 * Класс запрос на регистрацию и авторизацию пользователя
 */
public class AuthRequest {
    @Schema(example = "user",
            type = "string")
    @NotBlank(message = "Username can not be blank")
    private String username;

    @Schema(minLength = 8,
            type = "string",
            example = "password")
    @Size(min = 8, message = "password's length can not be less than 8")
    @NotBlank(message = "password can not be blank")
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
