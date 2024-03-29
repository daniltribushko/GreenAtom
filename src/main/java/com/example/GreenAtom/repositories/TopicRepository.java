package com.example.GreenAtom.repositories;

import com.example.GreenAtom.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tribushko Danil
 *
 * Репозиторий для работы с топиками
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByName(String name);
    Optional<Topic> findByName(String name);
}
