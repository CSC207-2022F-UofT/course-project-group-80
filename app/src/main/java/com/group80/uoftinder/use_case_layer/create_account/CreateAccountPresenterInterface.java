package com.group80.uoftinder.use_case_layer.create_account;

import com.group80.uoftinder.entities_layer.User;

/**
 * Provides methods for updating the view
 */
public interface CreateAccountPresenterInterface {

    // Email and Password Setup
    void prepareSuccessView(String success, User currentUser);

    void prepareCreateAccountFailureView(String error);

    void prepareEmailMissingView(String error);

    void preparePassword1MissingView(String error);

    void preparePassword2MissingView(String error);

    void preparePasswordMatchError(String error);

    // Questionnaire views
    void createAcademicQuestionnaire(User currentUser);

    void createFriendshipQuestionnaire(User currentUser);

    void createRomanticQuestionnaire(User currentUser);

    void prepareProfilePicUploadActivity(User currentUser);
}
