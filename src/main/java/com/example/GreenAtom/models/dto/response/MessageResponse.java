package com.example.GreenAtom.models.dto.response;

import com.example.GreenAtom.models.entities.Message;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * <p>
 * Dto ответа на получения сообщения
 */
public class MessageResponse {
    @Schema(description = "Id of message",
            name = "id",
            type = "long",
            example = "1")
    private final Long id;
    @Schema(description = "Author of message",
            name = "author",
            type = "string",
            example = "author")
    private final String author;
    @Schema(description = "Text of message",
            name = "text",
            type = "string",
            example = "hello")
    private final String text;
    @Schema(description = "Message creation date",
            name = "creation date",
            type = "localDateTime")
    private final LocalDateTime createdDate;

    public MessageResponse(Long id, String author, String text, LocalDateTime createdDate) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public static MessageResponse mapFromEntity(Message message) {
        return new MessageResponse(message.getId(),
                message.getText(),
                message.getAuthor().getUsername(),
                message.getCreatedDate());
    }
}
