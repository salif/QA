// SPDX-FileCopyrightText: 2020 Salif Mehmed <mail@salif.eu>
// SPDX-License-Identifier: MIT

package eu.salif.qa.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity {
    private User author;
    private String title;
    private String text;
    private LocalDateTime createdOn;
    private Integer views;
    private Integer answerCount;
    private boolean edited;

    public Question() {
        this.views = 0;
        this.answerCount = 0;
        this.edited = false;
    }

    public void set(User author, String title, String text, LocalDateTime createdOn) {
        this.setAuthor(author);
        this.setTitle(title);
        this.setText(text);
        this.setCreatedOn(createdOn);
    }

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "created_on", nullable = false)
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "views", nullable = false)
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Column(name = "answer_count", nullable = false)
    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    @Column(name = "edited", nullable = false)
    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}