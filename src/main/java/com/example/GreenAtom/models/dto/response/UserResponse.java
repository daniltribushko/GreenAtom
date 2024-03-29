package com.example.GreenAtom.models.dto.response;

import com.example.GreenAtom.models.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Tribushko Danil
 * Dto запроса на получение пользователей
 */
public class UserResponse {
    @Schema(example = "1",
            type = "long")
    private Long id;
    @Schema(example = "user",
            type = "string")
    private String username;

    public UserResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static UserResponse mapFromEntity(User user) {
        return new UserResponse(user.getId(), user.getUsername());
    }
}
