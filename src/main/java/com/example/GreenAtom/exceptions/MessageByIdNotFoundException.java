package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class MessageByIdNotFoundException extends AppException{
    public MessageByIdNotFoundException(Long id) {
        super(404, "Message with id: " + id + "not found");
    }
}
