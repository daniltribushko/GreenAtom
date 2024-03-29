package com.example.GreenAtom.services;

import com.example.GreenAtom.models.dto.response.UserResponse;
import com.example.GreenAtom.models.entities.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Tribushko Danil
 * Сервис для работы с пользователями
 */
public interface UserService extends UserDetailsService {
    void create(User user);

    UserResponse findById(
            @Min(value = 1, message = "Id can not be less than 1")
            Long id);

    UserResponse findByUserName(@NotBlank(message = "Username can not be blank")
                                String username);

    boolean isExistByUserName(@NotBlank(message = "Username can not be blank")
                              String username);
    User findUserByUserName(@NotBlank(message = "Username can not be blank")
                            String username);

}
