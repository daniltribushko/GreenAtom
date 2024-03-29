package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class UserNotMessageAuthorException extends AppException{
    public UserNotMessageAuthorException(String username, Long messageId) {
        super(409, "User " + username + " not message creator with id " + messageId);
    }
}
