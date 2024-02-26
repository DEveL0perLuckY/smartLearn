package com.learningplatform.app.smart_learn.repos;

import com.learningplatform.app.smart_learn.domain.Course;
import com.learningplatform.app.smart_learn.domain.LearningContent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LearningContentRepository extends MongoRepository<LearningContent, Integer> {

    LearningContent findFirstByCourse(Course course);

    Page<LearningContent> findByCourse(Course course, Pageable pageable);
}
