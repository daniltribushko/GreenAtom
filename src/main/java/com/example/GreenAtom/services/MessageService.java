package com.example.GreenAtom.services;

import com.example.GreenAtom.models.dto.request.MessageRequest;
import com.example.GreenAtom.models.dto.request.UpdateMessageRequest;
import com.example.GreenAtom.models.dto.response.MessagesResponse;
import com.example.GreenAtom.models.dto.response.TopicResponse;
import com.example.GreenAtom.models.entities.Message;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 *
 * Сервис для работы с сообщениями
 */
public interface MessageService {
    void delete(@NotBlank(message = "Username can not be blank")
            String username,
            @Min(value = 1, message = "Id can not be less than 1")
            Long id);
    TopicResponse createMessage(@NotBlank(message = "Username can not be blank")
                                String username,
                                @Min(value = 1, message = "Id can not be less than 1")
                                Long topicId,
                                @Valid
                                MessageRequest request);
    TopicResponse updateTopic(@NotBlank(message = "Username can not be blank")
                     String username,
                     @Min(value = 1, message = "Id can not be less than 1")
                     Long topicId,
                     @Valid
                     UpdateMessageRequest request);
    TopicResponse findById(@Min(value = 1, message = "Id can not be less than 1")
                           Long topicId,
                           @Min(value = 1, message = "Id can not be less than 1")
                           Long id);
    Message findMessageById(@Min(value = 1, message = "Id can not be less than 1")
                            Long id);
    MessagesResponse findAll(@Min(value = 0, message = "Page can not be less than 0")
                             int page,
                             @Min(value = 1, message = "Per page can not be less than 1")
                             int perPage,
                             @NotBlank(message = "Author can not be blank")
                             String author,
                             @NotBlank(message = "Topic can not be blank")
                             String topic,
                             @PastOrPresent
                             LocalDateTime createdDate);
}
