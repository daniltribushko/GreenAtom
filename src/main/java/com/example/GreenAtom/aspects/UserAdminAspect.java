package com.example.GreenAtom.aspects;

import com.example.GreenAtom.exceptions.UserNotAdminException;
import com.example.GreenAtom.models.entities.User;
import com.example.GreenAtom.services.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Tribushko Danil
 *
 * Аспект для проверки является ли пользователь администратором
 */
@Aspect
@Component
public class UserAdminAspect {
    private final UserService userService;

    @Autowired
    public UserAdminAspect(UserService userService) {
        this.userService = userService;
    }

    @Before(value = "@annotation(com.example.GreenAtom.aspects.annotations.CheckUserAdmin) && " +
            "args(username,..)", argNames = "username")
    public void checkIsUserAdmin(String username) {
        User user = userService.findUserByUserName(username);
        if (!user.isAdmin()) {
            throw new UserNotAdminException(username);
        }
    }
}
