package com.group80.uoftinder.login_use_case;

import com.group80.uoftinder.entities_layer.User;

/**
 * Provides methods to update view
 */
public interface LoginViewInterface {
    void updateUI(User currentUser);

    void showMessageToast(String message);

    void showEmailMessage(String error);

    void showPasswordMessage(String error);
}
