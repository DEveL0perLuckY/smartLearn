package com.learningplatform.app.smart_learn.repos;

import com.learningplatform.app.smart_learn.domain.User;
import com.learningplatform.app.smart_learn.domain.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserProfileRepository extends MongoRepository<UserProfile, Integer> {

    UserProfile findFirstByUser(User user);

}
