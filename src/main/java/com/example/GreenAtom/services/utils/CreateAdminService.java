package com.example.GreenAtom.services.utils;

import com.example.GreenAtom.models.entities.User;
import com.example.GreenAtom.services.RoleService;
import com.example.GreenAtom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Tribushko Danil
 *
 * Класс для создания администратора в бд при запуске приложения
 */
@Service
public class CreateAdminService implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public CreateAdminService(UserService userService,
                              RoleService roleService,
                              PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User("admin", passwordEncoder.encode("12345678"));
        user.setRoles(Set.of(roleService.findByName("USER"), roleService.findByName("ADMIN")));
        userService.create(user);
    }
}
