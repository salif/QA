package com.salifm.qa.model.binding;

import org.hibernate.validator.constraints.Length;

public class UserRegisterBindingModel {

    private String username;
    private String password;

    public UserRegisterBindingModel() {
    }

    @Length(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 3, max = 40, message = "Password length must be between 3 and 40 characters!")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
