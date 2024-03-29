package com.example.GreenAtom.repositories;

import com.example.GreenAtom.models.entities.Topic;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tribushko Danil
 *
 * Репозиторий для работы с топиками с пагинацией
 */
@Repository
public interface TopicPaginationRepository extends PagingAndSortingRepository<Topic, Long> {

}
