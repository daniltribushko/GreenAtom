package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class TopicByIdNotFoundException extends AppException{
    public TopicByIdNotFoundException(Long id) {
        super(404, "Topic with id " + id + " not found exception");
    }
}
