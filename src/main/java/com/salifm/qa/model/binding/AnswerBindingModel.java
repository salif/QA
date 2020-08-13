package com.salifm.qa.model.binding;

import org.hibernate.validator.constraints.Length;

public class AnswerBindingModel {
    private String text;

    public AnswerBindingModel() {
    }

    @Length(min = 3, max = 10000, message = "Answer length must be between 3 and 10000 characters!")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
