package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class UserByIdNotFoundException extends AppException{
    public UserByIdNotFoundException(Long id) {
        super(404, "User with id: " + id + " not found exception");
    }
}
