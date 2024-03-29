package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class UserByUserNameNotFoundExceptions extends AppException{
    public UserByUserNameNotFoundExceptions(String username) {
        super(404, "User: " + username + " not found");
    }
}
