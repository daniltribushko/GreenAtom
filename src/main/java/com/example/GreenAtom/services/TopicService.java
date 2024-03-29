package com.example.GreenAtom.services;

import com.example.GreenAtom.models.dto.request.CreateTopicRequest;
import com.example.GreenAtom.models.dto.request.UpdateTopicRequest;
import com.example.GreenAtom.models.dto.response.TopicResponse;
import com.example.GreenAtom.models.dto.response.TopicsResponse;
import com.example.GreenAtom.models.entities.Topic;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Tribushko Danil
 * <p>
 * Сервис для работы с топиками
 */
public interface TopicService {
    TopicResponse create(@NotBlank(message = "Username can not be blank")
                         String username,
                         @Valid CreateTopicRequest request);

    void delete(
            @NotBlank(message = "Username can not be blank")
            String username,
            @Min(value = 1, message = "Id can not be less than 1")
            Long id);

    TopicResponse update(@NotBlank(message = "Username can not be blank")
                         String username,
                         @Valid
                         UpdateTopicRequest request);

    TopicResponse findById(@Min(value = 1, message = "Id can not be less than 1")
                           Long id);

    TopicResponse findByName(@NotBlank(message = "Name can not be blank")
                             String name);

    Topic findTopicByName(@NotBlank(message = "Name can not be blank")
                          String name);

    Topic findTopicById(@Min(value = 1, message = "Id can not be less than 1")
                        Long id);

    TopicsResponse findAll(int page, int perPage);
}
