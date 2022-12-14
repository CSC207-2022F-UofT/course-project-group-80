package com.group80.uoftinder.use_case_layer.create_account;

import com.group80.uoftinder.entities_layer.User;

import java.util.List;

/**
 * Provides methods to creating a new user, setting up user information
 */
public interface CreateAccountInput {
    void createAccount(String email, String password1, String password2);


    void setBasicInfo(String name, String age, String identity, String type, User currentUser);

    void setAcademicInfo(User currentUser, int year, List<Integer> majors, int campus);

    void setFriendshipInfo(User currentUser, int year, List<Integer> majors, int campus,
                           List<Integer> interests, List<Integer> colours);

    void setRomanticInfo(User currentUser, int sexuality, List<Integer> majors, int campus,
                         List<Integer> interests, int distance, int relationship);
}