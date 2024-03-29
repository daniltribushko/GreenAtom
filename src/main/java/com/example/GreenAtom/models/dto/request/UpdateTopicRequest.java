package com.example.GreenAtom.models.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Tribushko Danil
 * <p>
 * Dto запроса на обновление топика
 */
public class UpdateTopicRequest {
    @Schema(description = "Id of topic",
            name = "id",
            type = "long",
            example = "1",
            minimum = "1")
    @Min(value = 1, message = "Id can not be less than 1")
    private final Long id;

    @Schema(description = "New name of topic",
            name = "name",
            type = "string",
            example = "text topic")
    @NotBlank(message = "Name can not be blank")
    private final String name;

    public UpdateTopicRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
