package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class UserNotAdminException extends AppException{
    public UserNotAdminException(String name) {
        super(401, "User: " + name + " not admin");
    }
}
