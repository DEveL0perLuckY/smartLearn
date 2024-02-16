package com.learningplatform.app.smart_learn.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class LearningContentDTO {

    private Integer contentId;

    @NotNull
    @Size(max = 255)
    private String contentTitle;

    private String contentDescription;

    @Size(max = 50)
    private String contentType;

    private Integer course;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(final Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(final String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(final String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(final Integer course) {
        this.course = course;
    }

}
