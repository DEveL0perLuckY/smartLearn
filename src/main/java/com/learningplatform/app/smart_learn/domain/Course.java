package com.learningplatform.app.smart_learn.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
public class Course {

    @Id
    private Integer courseId;

    @NotNull
    @Size(max = 255)
    private String courseTitle;

    private String courseDescription;

    @Size(max = 50)
    private String courseType;

    @DocumentReference(lazy = true, lookup = "{ 'course' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<UserProgress> courseUserProgresses;

    @DocumentReference(lazy = true, lookup = "{ 'course' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<LearningContent> courseLearningContents;

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

    public Set<UserProgress> getCourseUserProgresses() {
        return courseUserProgresses;
    }

    public void setCourseUserProgresses(final Set<UserProgress> courseUserProgresses) {
        this.courseUserProgresses = courseUserProgresses;
    }

    public Set<LearningContent> getCourseLearningContents() {
        return courseLearningContents;
    }

    public void setCourseLearningContents(final Set<LearningContent> courseLearningContents) {
        this.courseLearningContents = courseLearningContents;
    }

}
