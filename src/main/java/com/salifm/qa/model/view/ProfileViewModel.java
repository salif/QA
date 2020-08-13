package com.salifm.qa.model.view;

import java.util.List;

public class ProfileViewModel {
    private String username;
    private List<RolesViewModel> roles;
    private List<QuestionPreviewViewModel> questions;
    private List<QuestionPreviewViewModel> answeredQuestions;
    private String createdOn;

    public ProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RolesViewModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesViewModel> roles) {
        this.roles = roles;
    }

    public List<QuestionPreviewViewModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionPreviewViewModel> questions) {
        this.questions = questions;
    }

    public List<QuestionPreviewViewModel> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<QuestionPreviewViewModel> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
