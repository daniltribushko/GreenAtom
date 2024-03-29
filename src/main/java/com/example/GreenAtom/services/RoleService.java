package com.example.GreenAtom.services;

import com.example.GreenAtom.models.entities.Role;

/**
 * @author Tribushko Danil
 * Сервис для работы с ролями
 */
public interface RoleService {
    /**
     * Получение роли по название
     * @param name название роли
     * @return сущность роли
     */
    Role findByName(String name);
}
