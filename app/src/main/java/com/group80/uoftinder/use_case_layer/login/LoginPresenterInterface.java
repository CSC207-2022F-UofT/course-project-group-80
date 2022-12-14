package com.group80.uoftinder.use_case_layer.login;

import com.group80.uoftinder.entities_layer.User;

/**
 * Provides methods to prepare view changes
 */
public interface LoginPresenterInterface {
    void prepareSuccessView(String success, User currentUser);

    void prepareLoginFailureView(String error);

    void prepareEmailFailureView(String error);

    void preparePasswordFailureView(String error);
}
