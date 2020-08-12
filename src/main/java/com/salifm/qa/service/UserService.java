package com.salifm.qa.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void initUsers();
    void register(String username, String password);
    boolean exists(String username);
}
