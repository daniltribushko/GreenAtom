package com.example.GreenAtom.models.dto.response;

import java.util.List;

/**
 * @author Tribushko Danil
 *
 * Dto ответа на запрос на получение нескольких топиков
 */
public class TopicsResponse {
    private final List<TopicResponse> topics;

    public TopicsResponse(List<TopicResponse> topics){
        this.topics = topics;
    }

    public List<TopicResponse> getTopics() {
        return topics;
    }
}
