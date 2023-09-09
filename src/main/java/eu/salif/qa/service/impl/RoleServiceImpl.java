// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service.impl;

import eu.salif.qa.constants.Roles;
import eu.salif.qa.model.entity.Role;
import eu.salif.qa.model.view.RoleViewModel;
import eu.salif.qa.model.view.RolesViewModel;
import eu.salif.qa.model.view.UserPreviewViewModel;
import eu.salif.qa.repository.RoleRepository;
import eu.salif.qa.repository.UserRepository;
import eu.salif.qa.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initRoles() {
        if(this.roleRepository.count() == 0){
            this.roleRepository.saveAndFlush(new Role(Roles.ADMIN));
            this.roleRepository.saveAndFlush(new Role(Roles.SUPER_MOD));
            this.roleRepository.saveAndFlush(new Role(Roles.MOD));
            this.roleRepository.saveAndFlush(new Role(Roles.USER));
            this.roleRepository.saveAndFlush(new Role(Roles.TRANSLATOR));
        }
    }

    @Override
    public List<RolesViewModel> getAllRoles() {
        return this.roleRepository.findAll().stream()
                .map(role -> this.modelMapper.map(role, RolesViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleViewModel getRole(String id) {
        Role role = this.roleRepository.findById(id).orElseThrow();
        RoleViewModel roleViewModel = new RoleViewModel();
        roleViewModel.setAuthority(this.roleRepository.findById(id).orElseThrow().getAuthority());
        roleViewModel.setUsers(this.userRepository.findAllByOrderByCreatedOn().stream()
                .filter(u -> u.getAuthorities().contains(role))
                .map(u -> this.modelMapper.map(u, UserPreviewViewModel.class))
                .collect(Collectors.toList()));
        return roleViewModel;
    }
}
