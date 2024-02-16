package com.learningplatform.app.smart_learn.service;

import com.learningplatform.app.smart_learn.domain.User;
import com.learningplatform.app.smart_learn.domain.UserProfile;
import com.learningplatform.app.smart_learn.model.UserProfileDTO;
import com.learningplatform.app.smart_learn.repos.UserProfileRepository;
import com.learningplatform.app.smart_learn.repos.UserRepository;
import com.learningplatform.app.smart_learn.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(final UserProfileRepository userProfileRepository,
            final UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public List<UserProfileDTO> findAll() {
        final List<UserProfile> userProfiles = userProfileRepository.findAll(Sort.by("userProfileId"));
        return userProfiles.stream()
                .map(userProfile -> mapToDTO(userProfile, new UserProfileDTO()))
                .toList();
    }

    public UserProfileDTO get(final Integer userProfileId) {
        return userProfileRepository.findById(userProfileId)
                .map(userProfile -> mapToDTO(userProfile, new UserProfileDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserProfileDTO userProfileDTO) {
        final UserProfile userProfile = new UserProfile();
        mapToEntity(userProfileDTO, userProfile);
        return userProfileRepository.save(userProfile).getUserProfileId();
    }

    public void update(final Integer userProfileId, final UserProfileDTO userProfileDTO) {
        final UserProfile userProfile = userProfileRepository.findById(userProfileId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userProfileDTO, userProfile);
        userProfileRepository.save(userProfile);
    }

    public void delete(final Integer userProfileId) {
        userProfileRepository.deleteById(userProfileId);
    }

    private UserProfileDTO mapToDTO(final UserProfile userProfile,
            final UserProfileDTO userProfileDTO) {
        userProfileDTO.setUserProfileId(userProfile.getUserProfileId());
        userProfileDTO.setFullName(userProfile.getFullName());
        userProfileDTO.setDateOfBirth(userProfile.getDateOfBirth());
        userProfileDTO.setLearningStyle(userProfile.getLearningStyle());
        userProfileDTO.setUser(userProfile.getUser() == null ? null : userProfile.getUser().getUserId());
        return userProfileDTO;
    }

    private UserProfile mapToEntity(final UserProfileDTO userProfileDTO,
            final UserProfile userProfile) {
        userProfile.setFullName(userProfileDTO.getFullName());
        userProfile.setDateOfBirth(userProfileDTO.getDateOfBirth());
        userProfile.setLearningStyle(userProfileDTO.getLearningStyle());
        final User user = userProfileDTO.getUser() == null ? null : userRepository.findById(userProfileDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        userProfile.setUser(user);
        return userProfile;
    }

}
