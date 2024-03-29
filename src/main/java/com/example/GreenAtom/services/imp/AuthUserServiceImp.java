package com.example.GreenAtom.services.imp;

import com.example.GreenAtom.exceptions.UserAlreadyExistException;
import com.example.GreenAtom.exceptions.WrongUsernameOrPasswordException;
import com.example.GreenAtom.models.dto.request.AuthRequest;
import com.example.GreenAtom.models.dto.response.JwtTokenResponse;
import com.example.GreenAtom.models.dto.response.UserResponse;
import com.example.GreenAtom.models.entities.User;
import com.example.GreenAtom.services.AuthUserService;
import com.example.GreenAtom.services.RoleService;
import com.example.GreenAtom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Tribushko Danil
 * Сервис для авторизации и регистрации пользователей
 */
@Service
public class AuthUserServiceImp implements AuthUserService {
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthUserServiceImp(JwtTokenService jwtTokenService,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager,
                              UserService userService,
                              RoleService roleService) {
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserResponse signUp(AuthRequest request) {
        String username = request.getUsername();
        boolean isUserExist = userService.isExistByUserName(username);
        if (isUserExist){
            throw new UserAlreadyExistException(username);
        }
        User user = new User(username, passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(roleService.findByName("USER")));
        userService.create(user);
        return UserResponse.mapFromEntity(user);
    }

    @Override
    public JwtTokenResponse signIn(AuthRequest request) {
        String username = request.getUsername();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    request.getPassword()));
        } catch (BadCredentialsException e){
            throw new WrongUsernameOrPasswordException();
        }
        UserDetails user = userService.loadUserByUsername(username);
        return new JwtTokenResponse(jwtTokenService.generateToken(user));
    }
}
