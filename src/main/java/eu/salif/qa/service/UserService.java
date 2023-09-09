// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service;

import eu.salif.qa.model.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void initUsers();
    void register(String username, String password);
    boolean exists(String username);
    String getUserId(String username);
    UserViewModel getProfile(String id);
}
