package com.learningplatform.app.smart_learn.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
public class User {

    @Id
    private Integer userId;

    @NotNull
    @Size(max = 50)
    private String username;

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @DocumentReference(lazy = true)
    private Set<Role> roleId;

    @DocumentReference(lazy = true, lookup = "{ 'user' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<UserProfile> userUserProfiles;

    @DocumentReference(lazy = true, lookup = "{ 'user' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<UserProgress> userUserProgresses;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Role> getRoleId() {
        return roleId;
    }

    public void setRoleId(final Set<Role> roleId) {
        this.roleId = roleId;
    }

    public Set<UserProfile> getUserUserProfiles() {
        return userUserProfiles;
    }

    public void setUserUserProfiles(final Set<UserProfile> userUserProfiles) {
        this.userUserProfiles = userUserProfiles;
    }

    public Set<UserProgress> getUserUserProgresses() {
        return userUserProgresses;
    }

    public void setUserUserProgresses(final Set<UserProgress> userUserProgresses) {
        this.userUserProgresses = userUserProgresses;
    }

}
