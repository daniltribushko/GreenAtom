package com.example.GreenAtom.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Tribushko Danil
 * <p>
 * Dto запроса на работу с топиком
 */
public class CreateTopicRequest {
    @Schema(description = "Name of topic",
            name = "name",
            type = "string",
            example = "test topic")
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Schema(description = "First message in topic",
            name = "message")
    @Valid
    private MessageRequest message;

    public CreateTopicRequest(String name, MessageRequest message) {
        this.name = name;
        this.message = message;
    }

    public CreateTopicRequest(){}
    public String getName() {
        return name;
    }

    public MessageRequest getMessage() {
        return message;
    }
}
