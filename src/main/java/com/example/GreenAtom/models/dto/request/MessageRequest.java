package com.example.GreenAtom.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Tribushko Danil
 * <p>
 * Dto запроса на работу с сообщением
 */
public class MessageRequest {
    @Schema(description = "Text of message",
            name = "text",
            type = "string",
            example = "hello")
    @NotBlank(message = "Text can not be blank")
    private String text;

    public MessageRequest(String text) {
        this.text = text;
    }

    public MessageRequest() {
    }

    public String getText() {
        return text;
    }
}
