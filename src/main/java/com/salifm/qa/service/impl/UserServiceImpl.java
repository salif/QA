package com.salifm.qa.service.impl;

import com.salifm.qa.constants.Roles;
import com.salifm.qa.constants.Users;
import com.salifm.qa.model.entity.Role;
import com.salifm.qa.model.entity.User;
import com.salifm.qa.repository.RoleRepository;
import com.salifm.qa.repository.UserRepository;
import com.salifm.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public void initUsers() {
        if(this.userRepository.count() == 0){
            register(Users.ADMIN_USERNAME, Users.ADMIN_PASSWORD);
            if (this.userRepository.findByUsername(Users.ADMIN_USERNAME).isPresent()) {
                User admin = this.userRepository.findByUsername(Users.ADMIN_USERNAME).get();
                admin.addAuthority(this.roleRepository.findByAuthority(Roles.ADMIN));
                this.userRepository.saveAndFlush(admin);
            }
        }
    }

    @Override
    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setAuthorities(new HashSet<>(Set.of(this.roleRepository.findByAuthority("USER"))));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public boolean exists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }
}
