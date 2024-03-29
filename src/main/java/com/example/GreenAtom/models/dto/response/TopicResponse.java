package com.example.GreenAtom.models.dto.response;

import com.example.GreenAtom.models.entities.Topic;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 *
 */
public class TopicResponse {
    @Schema(description = "Topic's id",
            name = "id",
            type = "long",
            example = "1")
    private Long id;
    @Schema(description = "Topic's name",
            name = "name",
            type = "string",
            example = "test topic")
    private String name;
    @Schema(description = "Topic's messages")
    private List<MessageResponse> messages;

    public TopicResponse(Long id, String name, List<MessageResponse> messages) {
        this.id = id;
        this.name = name;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public static TopicResponse mapFromEntity(Topic topic) {
        return new TopicResponse(topic.getId(),
                topic.getName(),
                topic.getMessages()
                        .stream()
                        .map(MessageResponse::mapFromEntity)
                        .toList());
    }
}
