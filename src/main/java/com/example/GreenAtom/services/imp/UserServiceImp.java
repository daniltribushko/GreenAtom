package com.example.GreenAtom.services.imp;

import com.example.GreenAtom.exceptions.UserByIdNotFoundException;
import com.example.GreenAtom.exceptions.UserByUserNameNotFoundExceptions;
import com.example.GreenAtom.models.dto.response.UserResponse;
import com.example.GreenAtom.models.entities.User;
import com.example.GreenAtom.repositories.UserRepository;
import com.example.GreenAtom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Tribushko Danil
 * Реализация сервиса по работе с пользователями
 */
@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserByIdNotFoundException(id));
        return UserResponse.mapFromEntity(user);
    }

    @Override
    public UserResponse findByUserName(String userName) {
        return UserResponse.mapFromEntity(findUserByUserName(userName));
    }

    @Override
    public boolean isExistByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findUserByUserName(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                        .toList());
    }

    public User findUserByUserName(String userName){
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserByUserNameNotFoundExceptions(userName));
    }
}
