package com.salifm.qa.service.impl;

import com.salifm.qa.constants.Roles;
import com.salifm.qa.model.entity.Role;
import com.salifm.qa.model.view.RoleViewModel;
import com.salifm.qa.model.view.RolesViewModel;
import com.salifm.qa.model.view.UserPreviewViewModel;
import com.salifm.qa.repository.RoleRepository;
import com.salifm.qa.repository.UserRepository;
import com.salifm.qa.service.RoleService;
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
            this.roleRepository.saveAndFlush(new Role(Roles.USER));
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
