package com.salifm.qa.service;

import com.salifm.qa.model.view.ProfileViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void initUsers();
    void register(String username, String password);
    boolean exists(String username);
    String getUserId(String username);
    ProfileViewModel getProfile(String id);
}
