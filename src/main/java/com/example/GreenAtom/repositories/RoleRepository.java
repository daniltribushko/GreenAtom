package com.example.GreenAtom.repositories;

import com.example.GreenAtom.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Tribushko Danil
 * Репозиторий для работы с сущностями ролей
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Поиск роли по названию
     * @param name название роли
     * @return сущность роли
     */
    Optional<Role> findByName(String name);
}
