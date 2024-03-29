package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class UserAlreadyExistException extends AppException{
    public UserAlreadyExistException(String username) {
        super(409, "User " + username + " already exist");
    }
}
