package com.learningplatform.app.smart_learn.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CourseDTO {

    private Integer courseId;

    @NotNull
    @Size(max = 255)
    private String courseTitle;

    private String courseDescription;

    @Size(max = 50)
    private String courseType;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(final Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(final String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(final String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(final String courseType) {
        this.courseType = courseType;
    }

}
