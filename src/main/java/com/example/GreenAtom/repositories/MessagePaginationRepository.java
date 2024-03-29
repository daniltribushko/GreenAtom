package com.example.GreenAtom.repositories;

import com.example.GreenAtom.models.entities.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tribushko Danil
 *
 * Репозиторий для работы с сообщениями с пагинацией
 */
@Repository
public interface MessagePaginationRepository extends PagingAndSortingRepository<Message, Long> {
}
