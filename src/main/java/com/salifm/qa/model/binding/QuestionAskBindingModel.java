package com.salifm.qa.model.binding;

import org.hibernate.validator.constraints.Length;

public class QuestionAskBindingModel {

    private String title;
    private String text;

    public QuestionAskBindingModel() {
    }

    @Length(min = 3, max = 250, message = "Title length must be between 3 and 250 characters!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(max = 50000, message = "Text length must be between 0 and 50000 characters!")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
