package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class TopicByNameNotFoundException extends AppException{
    public TopicByNameNotFoundException(String name) {
        super(404, "Topic: " + name + " not found");
    }
}
