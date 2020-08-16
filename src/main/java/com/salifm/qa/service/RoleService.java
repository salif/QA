// SPDX-FileCopyrightText: 2020 Salif Mehmed <salifm@salifm.com>
// SPDX-License-Identifier: MIT

package com.salifm.qa.service;

import com.salifm.qa.model.view.RoleViewModel;
import com.salifm.qa.model.view.RolesViewModel;

import java.util.List;

public interface RoleService {
    void initRoles();
    List<RolesViewModel> getAllRoles();
    RoleViewModel getRole(String id);
}
