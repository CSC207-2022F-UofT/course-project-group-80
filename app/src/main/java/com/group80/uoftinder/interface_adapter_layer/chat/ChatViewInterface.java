package com.group80.uoftinder.interface_adapter_layer.chat;

import com.group80.uoftinder.interface_adapter_layer.firebase.ProfileImageViewInterface;
import com.group80.uoftinder.view_layer.chat.ContactsActivity;

/**
 * A view for chat window the provides abstraction of behaviours
 */
public interface ChatViewInterface extends ProfileImageViewInterface {
    /**
     * Returns the chat room identifier between the two users
     *
     * @param uid1 the uid of one user
     * @param uid2 the uid of the other user
     * @return the chat room identifier between the two users
     */
    static String getChatRoom(String uid1, String uid2) {
        return uid1.compareTo(uid2) < 0 ? uid1 + uid2 : uid2 + uid1;
    }

    /**
     * Displays the contact information of the given contact name
     *
     * @param contactName the name of the contact
     * @param contactUid  the uid of the contact
     */
    void showContactInfo(String contactName, String contactUid);

    /**
     * Notifies the listeners that message(s) have been added
     */
    void notifyMessageAdded();

    /**
     * Displays a new message
     *
     * @param message the message body of the new message
     */
    void displayNewMessage(String message);

    /**
     * Enters the {@link ContactsActivity}
     */
    void enterContactView();
}
