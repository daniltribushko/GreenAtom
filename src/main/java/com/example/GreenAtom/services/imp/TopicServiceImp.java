package com.example.GreenAtom.services.imp;

import com.example.GreenAtom.aspects.annotations.CheckUserAdmin;
import com.example.GreenAtom.exceptions.TopicAlreadyExistException;
import com.example.GreenAtom.exceptions.TopicByIdNotFoundException;
import com.example.GreenAtom.exceptions.TopicByNameNotFoundException;
import com.example.GreenAtom.models.dto.request.CreateTopicRequest;
import com.example.GreenAtom.models.dto.request.MessageRequest;
import com.example.GreenAtom.models.dto.request.UpdateTopicRequest;
import com.example.GreenAtom.models.dto.response.TopicResponse;
import com.example.GreenAtom.models.dto.response.TopicsResponse;
import com.example.GreenAtom.models.entities.Message;
import com.example.GreenAtom.models.entities.Topic;
import com.example.GreenAtom.models.entities.User;
import com.example.GreenAtom.repositories.TopicPaginationRepository;
import com.example.GreenAtom.repositories.TopicRepository;
import com.example.GreenAtom.services.TopicService;
import com.example.GreenAtom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Tribushko Danil
 *
 * Реализация сервиса по работе с топиками
 */
@Service
public class TopicServiceImp implements TopicService {
    private final TopicRepository topicRepository;
    private final UserService userService;
    private final TopicPaginationRepository topicPaginationRepository;
    @Autowired
    public TopicServiceImp(TopicRepository topicRepository,
                           UserService userService,
                           TopicPaginationRepository topicPaginationRepository){
        this.topicRepository = topicRepository;
        this.userService = userService;
        this.topicPaginationRepository = topicPaginationRepository;
    }

    @Override
    public TopicResponse create(String username, CreateTopicRequest request) {
        User user = userService.findUserByUserName(username);
        String topicName = request.getName();
        MessageRequest messageRequest = request.getMessage();
        if (topicRepository.existsByName(topicName)){
            throw new TopicAlreadyExistException(topicName);
        }
        Topic topic = new Topic(topicName);
        Message message = new Message(messageRequest.getText(), LocalDateTime.now(), user);
        message.setTopic(topic);
        topic.setMessages(Set.of(message));
        topicRepository.save(topic);
        return TopicResponse.mapFromEntity(topic);
    }

    @Override
    @CheckUserAdmin
    public void delete(String username, Long id) {
        Topic topic = findTopicById(id);
        topicRepository.delete(topic);
    }

    @Override
    @CheckUserAdmin
    public TopicResponse update(String username, UpdateTopicRequest request) {
        Topic topic = findTopicById(request.getId());
        topic.setName(request.getName());
        topicRepository.save(topic);
        return TopicResponse.mapFromEntity(topic);
    }

    @Override
    public TopicResponse findById(Long id) {
        return TopicResponse.mapFromEntity(findTopicById(id));
    }

    @Override
    public TopicResponse findByName(String name) {
        return TopicResponse.mapFromEntity(findTopicByName(name));
    }

    @Override
    public Topic findTopicByName(String name) {
        return topicRepository.findByName(name)
                .orElseThrow(() -> new TopicByNameNotFoundException(name));
    }

    @Override
    public Topic findTopicById(Long id){
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicByIdNotFoundException(id));
    }

    @Override
    public TopicsResponse findAll(int page, int perPage) {
        return new TopicsResponse(topicPaginationRepository.findAll(PageRequest.of(page, perPage))
                .stream()
                .map(TopicResponse::mapFromEntity)
                .toList());
    }
}
