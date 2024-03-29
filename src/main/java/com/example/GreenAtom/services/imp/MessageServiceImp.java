package com.example.GreenAtom.services.imp;

import com.example.GreenAtom.aspects.annotations.CheckUserMessageAuthor;
import com.example.GreenAtom.exceptions.MessageByIdInTopicNotFoundException;
import com.example.GreenAtom.exceptions.MessageByIdNotFoundException;
import com.example.GreenAtom.models.dto.request.MessageRequest;
import com.example.GreenAtom.models.dto.request.UpdateMessageRequest;
import com.example.GreenAtom.models.dto.response.MessageResponse;
import com.example.GreenAtom.models.dto.response.MessagesResponse;
import com.example.GreenAtom.models.dto.response.TopicResponse;
import com.example.GreenAtom.models.entities.Message;
import com.example.GreenAtom.models.entities.Topic;
import com.example.GreenAtom.models.entities.User;
import com.example.GreenAtom.repositories.MessageRepository;
import com.example.GreenAtom.services.MessageService;
import com.example.GreenAtom.services.TopicService;
import com.example.GreenAtom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tribushko Danil
 * <p>
 * Реализация сервиса по работе с сообщениями
 */
@Service
public class MessageServiceImp implements MessageService {
    private final MessageRepository messageRepository;
    private final TopicService topicService;
    private final UserService userService;

    @Autowired
    public MessageServiceImp(MessageRepository messageRepository,
                             TopicService topicService,
                             UserService userService) {
        this.messageRepository = messageRepository;
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    @CheckUserMessageAuthor
    public void delete(String username, Long id) {
        Message message = findMessageById(id);
        messageRepository.delete(message);
    }

    @Override
    public TopicResponse createMessage(String username, Long topicId, MessageRequest request) {
        Topic topic = topicService.findTopicById(topicId);
        User user = userService.findUserByUserName(username);
        Message message = new Message(request.getText(), LocalDateTime.now(), user);
        Set<Message> messages = topic.getMessages();
        message.setTopic(topic);
        messages.add(message);
        topic.setMessages(messages);
        messageRepository.save(message);
        MessageResponse messageResponse = MessageResponse.mapFromEntity(message);
        return new TopicResponse(topic.getId(), topic.getName(), List.of(messageResponse));
    }

    @Override
    @CheckUserMessageAuthor
    public TopicResponse updateTopic(String username, Long topicId, UpdateMessageRequest request) {
        Long messageId = request.getId();
        Topic topic = topicService.findTopicById(messageId);
        String topicName = topic.getName();
        Message message = messageRepository.findByIdAndTopic(messageId, topic)
                .orElseThrow(() -> new MessageByIdInTopicNotFoundException(messageId, topicName));
        String newText = request.getText();
        if (newText != null){
            message.setText(newText);
            messageRepository.save(message);
        }
        return new TopicResponse(topic.getId(), topicName, List.of(MessageResponse.mapFromEntity(message)));
    }

    @Override
    public TopicResponse findById(Long topicId, Long id) {
        Topic topic = topicService.findTopicById(topicId);
        MessageResponse messageResponse = MessageResponse.mapFromEntity(messageRepository.findByIdAndTopic(id, topic)
                .orElseThrow(() -> new MessageByIdInTopicNotFoundException(id, topic.getName())));
        return new TopicResponse(topic.getId(), topic.getName(), List.of(messageResponse));
    }

    @Override
    public Message findMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageByIdNotFoundException(id));
    }

    @Override
    public MessagesResponse findAll(int page, int perPage, String author, String topic, LocalDateTime createdDate) {
        return  new MessagesResponse(messageRepository.findAll(PageRequest.of(page, perPage))
                .stream()
                .filter(m -> (author == null || Objects.equals(m.getAuthor().getUsername(), author)) &&
                        (topic == null || Objects.equals(m.getTopic().getName(), topic)) &&
                        (createdDate == null || Objects.equals(m.getCreatedDate(), createdDate)))
                .map(MessageResponse::mapFromEntity)
                .toList());
    }
}
