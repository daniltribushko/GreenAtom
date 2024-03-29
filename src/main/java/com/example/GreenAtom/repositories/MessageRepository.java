package com.example.GreenAtom.repositories;

import com.example.GreenAtom.models.entities.Message;
import com.example.GreenAtom.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tribushko Danil
 *
 * Репозиторий для работы с сообщениями
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByIdAndTopic(Long id, Topic topic);
}
