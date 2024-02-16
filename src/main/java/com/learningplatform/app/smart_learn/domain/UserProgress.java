package com.learningplatform.app.smart_learn.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
public class UserProgress {

    @Id
    private Integer progressId;

    private Boolean completionStatus;

    @DocumentReference(lazy = true)
    private User user;

    @DocumentReference(lazy = true)
    private Course course;

    public Integer getProgressId() {
        return progressId;
    }

    public void setProgressId(final Integer progressId) {
        this.progressId = progressId;
    }

    public Boolean getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(final Boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(final Course course) {
        this.course = course;
    }

}
