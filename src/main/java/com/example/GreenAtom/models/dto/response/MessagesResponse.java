package com.example.GreenAtom.models.dto.response;

import java.util.List;

/**
 * @author Tribushko Danil
 *
 * Dto ответа на запрос на получение нескольких сообщений
 */
public class MessagesResponse {
    private final List<MessageResponse> messages;

    public MessagesResponse(List<MessageResponse> messages){
        this.messages = messages;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }
}
