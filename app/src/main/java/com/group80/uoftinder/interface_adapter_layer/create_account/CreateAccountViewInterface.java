package com.group80.uoftinder.interface_adapter_layer.create_account;

import com.group80.uoftinder.entities_layer.User;

/**
 * Provides methods to notify of user interaction
 */
public interface CreateAccountViewInterface {
    void showMessage(String message);

    void basicInfoUI(User currentUser);

    void showEmailMessage(String error);

    void showPassword1Message(String error);

    void showPassword2Message(String error);

    // Questionnaire UI's
    void academicQuestionnaireUI(User currentUser);

    void friendshipQuestionnaireUI(User currentUser);

    void romanticQuestionnaireUI(User currentUser);

    void uploadProfilePicture(User currentUser);
}
