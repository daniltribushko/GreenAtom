package com.example.GreenAtom.aspects;

import com.example.GreenAtom.exceptions.UserNotMessageAuthorException;
import com.example.GreenAtom.models.dto.request.UpdateMessageRequest;
import com.example.GreenAtom.models.entities.Message;
import com.example.GreenAtom.models.entities.User;
import com.example.GreenAtom.services.MessageService;
import com.example.GreenAtom.services.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class UserTopicMessageAspect {
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public UserTopicMessageAspect(UserService userService,
                                  MessageService messageService){
        this.userService = userService;
        this.messageService = messageService;
    }

    @Before(value = "@annotation(com.example.GreenAtom.aspects.annotations.CheckUserMessageAuthor) && " +
            "args(username, id,..)", argNames = "username, id")
    public void checkUserIsMessageAuthorAspect(String username, Long id) {
        checkUser(username, id);
    }


    @Before(value = "@annotation(com.example.GreenAtom.aspects.annotations.CheckUserMessageAuthor) && " +
            "args(username, request,..)", argNames = "username, request")
    public void checkUserIsMessageAuthorAspect(String username, UpdateMessageRequest request) {
        checkUser(username, request.getId());
    }

    private void checkUser(String username, Long id){
        User user = userService.findUserByUserName(username);
        Message message = messageService.findMessageById(id);
        if (!Objects.equals(user.getId(), message.getAuthor().getId()) && !user.isAdmin()){
            throw new UserNotMessageAuthorException(username, id);
        }
    }
}