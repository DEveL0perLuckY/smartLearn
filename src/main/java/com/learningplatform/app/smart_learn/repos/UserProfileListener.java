package com.learningplatform.app.smart_learn.repos;

import com.learningplatform.app.smart_learn.domain.UserProfile;
import com.learningplatform.app.smart_learn.service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class UserProfileListener extends AbstractMongoEventListener<UserProfile> {

    private final PrimarySequenceService primarySequenceService;

    public UserProfileListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<UserProfile> event) {
        if (event.getSource().getUserProfileId() == null) {
            event.getSource().setUserProfileId((int)primarySequenceService.getNextValue());
        }
    }

}
