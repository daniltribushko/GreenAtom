package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class MessageByIdInTopicNotFoundException extends AppException{
    public MessageByIdInTopicNotFoundException(Long id, String topicName) {
        super(404, "Message with id " + id + " in topic " + topicName + " not found");
    }
}
