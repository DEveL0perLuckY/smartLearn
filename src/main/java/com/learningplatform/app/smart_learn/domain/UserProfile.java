package com.learningplatform.app.smart_learn.domain;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
public class UserProfile {

    @Id
    private Integer userProfileId;

    @Size(max = 100)
    private String fullName;

    private LocalDateTime dateOfBirth;

    @Size(max = 50)
    private String learningStyle;

    @DocumentReference(lazy = true)
    private User user;

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(final Integer userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLearningStyle() {
        return learningStyle;
    }

    public void setLearningStyle(final String learningStyle) {
        this.learningStyle = learningStyle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

}
