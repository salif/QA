// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.service;

import eu.salif.qa.model.view.RoleViewModel;
import eu.salif.qa.model.view.RolesViewModel;

import java.util.List;

public interface RoleService {
    void initRoles();
    List<RolesViewModel> getAllRoles();
    RoleViewModel getRole(String id);
}
