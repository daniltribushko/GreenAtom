package com.example.GreenAtom.services.imp;

import com.example.GreenAtom.exceptions.RoleByNameNotFoundException;
import com.example.GreenAtom.models.entities.Role;
import com.example.GreenAtom.repositories.RoleRepository;
import com.example.GreenAtom.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tribushko
 * Реализация сервиса по работе с ролями
 */
@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleByNameNotFoundException(name));
    }
}
