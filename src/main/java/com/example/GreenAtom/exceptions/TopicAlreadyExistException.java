package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class TopicAlreadyExistException extends AppException{
    public TopicAlreadyExistException(String name) {
        super(409, "Topic: " + name + " already exist");
    }
}
