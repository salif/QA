package com.salifm.qa.model.view;

public class QuestionViewModel {
    private String id;
    private String title;
    private String text;
    private String authorName;
    private String authorId;
    private String answersCount;

    public QuestionViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(String answersCount) {
        this.answersCount = answersCount;
    }
}
