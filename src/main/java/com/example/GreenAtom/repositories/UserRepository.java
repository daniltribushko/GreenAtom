package com.example.GreenAtom.repositories;

import com.example.GreenAtom.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tribushko Danil
 * Репозиторий для работы с пользователями
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Получение пользователя по его имени
     * @param username имя пользователя
     * @return сущность пользователя
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверка наличия сущности пользователя в бд с указанным именем
     * @param username имя пользователя
     * @return сущность пользователя
     */
    boolean existsByUsername(String username);
}
