// SPDX-FileCopyrightText: 2020 Salif Mehmed <salifm@salifm.com>
// SPDX-License-Identifier: MIT

package com.salifm.qa.service;

import com.salifm.qa.model.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void initUsers();
    void register(String username, String password);
    boolean exists(String username);
    String getUserId(String username);
    UserViewModel getProfile(String id);
}
