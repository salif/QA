// SPDX-FileCopyrightText: 2020 Salif Mehmed <salifm@salifm.com>
// SPDX-License-Identifier: MIT

package com.salifm.qa.model.view;

public class AnswerViewModel {
    private String authorName;
    private String authorId;
    private String text;
    private String createdOn;
    private boolean edited;

    public AnswerViewModel() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
