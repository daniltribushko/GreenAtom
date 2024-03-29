package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class WrongUsernameOrPasswordException extends AppException{
    public WrongUsernameOrPasswordException() {
        super(401, "Wrong username or password");
    }
}
