package com.learningplatform.app.smart_learn.rest;

import com.learningplatform.app.smart_learn.model.UserProfileDTO;
import com.learningplatform.app.smart_learn.service.UserProfileService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/userProfiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileResource {

    private final UserProfileService userProfileService;

    public UserProfileResource(final UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAllUserProfiles() {
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("/{userProfileId}")
    public ResponseEntity<UserProfileDTO> getUserProfile(
            @PathVariable(name = "userProfileId") final Integer userProfileId) {
        return ResponseEntity.ok(userProfileService.get(userProfileId));
    }

    @PostMapping
    public ResponseEntity<Integer> createUserProfile(
            @RequestBody @Valid final UserProfileDTO userProfileDTO) {
        final Integer createdUserProfileId = userProfileService.create(userProfileDTO);
        return new ResponseEntity<>(createdUserProfileId, HttpStatus.CREATED);
    }

    @PutMapping("/{userProfileId}")
    public ResponseEntity<Integer> updateUserProfile(
            @PathVariable(name = "userProfileId") final Integer userProfileId,
            @RequestBody @Valid final UserProfileDTO userProfileDTO) {
        userProfileService.update(userProfileId, userProfileDTO);
        return ResponseEntity.ok(userProfileId);
    }

    @DeleteMapping("/{userProfileId}")
    public ResponseEntity<Void> deleteUserProfile(
            @PathVariable(name = "userProfileId") final Integer userProfileId) {
        userProfileService.delete(userProfileId);
        return ResponseEntity.noContent().build();
    }

}
