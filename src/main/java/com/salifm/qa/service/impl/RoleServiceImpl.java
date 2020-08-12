package com.salifm.qa.service.impl;

import com.salifm.qa.constants.Roles;
import com.salifm.qa.model.entity.Role;
import com.salifm.qa.repository.RoleRepository;
import com.salifm.qa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRoles() {
        if(this.roleRepository.count() == 0){
            this.roleRepository.saveAndFlush(new Role(Roles.ADMIN));
            this.roleRepository.saveAndFlush(new Role(Roles.USER));
        }
    }
}
