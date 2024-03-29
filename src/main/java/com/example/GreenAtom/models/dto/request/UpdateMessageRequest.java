package com.example.GreenAtom.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Tribushko Danil
 * <p>
 * Dto запроса на обновление сообщения
 */
public class UpdateMessageRequest {
    @Schema(description = "Id of message",
            name = "id",
            type = "long",
            example = "1",
            minimum = "1")
    @Min(value = 1, message = "Id can not be blank")
    private final Long id;

    @Schema(description = "Text of message",
            name = "text",
            type = "string",
            example = "hello")
    @NotBlank(message = "Text can not be blank")
    private final String text;

    public UpdateMessageRequest(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
