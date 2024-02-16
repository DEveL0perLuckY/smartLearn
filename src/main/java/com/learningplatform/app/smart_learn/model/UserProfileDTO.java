package com.learningplatform.app.smart_learn.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;


public class UserProfileDTO {

    private Integer userProfileId;

    @Size(max = 100)
    private String fullName;

    private LocalDateTime dateOfBirth;

    @Size(max = 50)
    private String learningStyle;

    private Integer user;

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

    public Integer getUser() {
        return user;
    }

    public void setUser(final Integer user) {
        this.user = user;
    }

}
