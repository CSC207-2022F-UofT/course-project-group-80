package com.group80.uoftinder.use_case_layer.feed;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.group80.uoftinder.use_case_layer.chat.MessageFactory;
import com.group80.uoftinder.entities_layer.User;
import com.group80.uoftinder.use_case_layer.firebase.firestore.FirestoreDbWriter;
import com.group80.uoftinder.use_case_layer.firebase.realtime.RealtimeDbValueObserver;
import com.group80.uoftinder.use_case_layer.firebase.realtime.UserRealtimeDbFacade;

import com.group80.uoftinder.use_case_layer.firebase.realtime.ChatMessageWriter;

import java.util.List;

/**
 * The MatchInteractor class is used to create a match between the current user and the user
 * they have liked, if that user has also liked them
 */
public class MatchInteractor {
    /**
     * This method is called when the currentUser has clicked like while displaying the
     * profile of user2, indicating that we need to check for a match.
     *
     * If user2 has already liked currentUser, then we add the two users to each other's
     * match list, re-upload the users to the database, and send an introductory message on
     * behalf of currentUser
     *
     * Preconditions
     *   - currentUser.getLiked().contains(user2.getUid());
     * @param currentUser is the user currently using the app who clicked like
     * @param user2 is the user whose liked list we need to check and possibly create a match with
     * @return a boolean indicating whether a match has been created
     */
    public static boolean checkForMatchAndCreate(User currentUser, User user2) {
        if (user2.getLiked().contains(currentUser.getUid())) { // if user2 has liked currentUser
            List<String> user1MatchList = currentUser.getMatches();
            List<String> user2MatchList = user2.getMatches();
            user1MatchList.add(user2.getUid()); // add both users to each other's match lists
            user2MatchList.add(currentUser.getUid());

            UserRealtimeDbFacade.uploadUser(user2); // re-upload users to the database
            UserRealtimeDbFacade.uploadUser(currentUser);

            // send a message from currentUser to user2
            sendIntroMessage(currentUser.getUid(), user2.getUid(), "Hey! We matched with each other!");
            // update contact lists so that user2 shows up in contact list
            FirestoreDbWriter.updateContactsLists(currentUser.getUid(), user2.getUid());
            return true;
        }
        return false;
    }

    /**
     * Send a preliminary message to the displayed user upon match
     * Uses ChatMessageWriter use case
     * @param selfUid is the uid of the current user
     * @param contactUid is the uid of the displayed user
     * @param message is the String to send from current user to the displayed user
     */
    private static void sendIntroMessage(String selfUid, String contactUid, String message) {
        String chatRoom = selfUid.compareTo(contactUid) < 0 ? selfUid + contactUid : contactUid + selfUid;

        ChatMessageWriter chatMessageWriter = new ChatMessageWriter(new RealtimeDbValueObserver() {
            @Override
            public void onRealtimeDbDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onRealtimeDbCancelled(@NonNull DatabaseError error) {
            }
        }, chatRoom);

        chatMessageWriter.write(MessageFactory.createMessage(message, selfUid));
    }

    /**
     * Adds displayedUser to currentUser's viewed list.
     * If liked is true, also adds displayedUser to currentUser's liked list.
     * @param currentUser is the user that is currently logged in.
     * @param displayedUser is the user that is currently being displayed to currentUser.
     * @param liked is True when currentUser 'likes' displayedUser, False otherwise.
     */
    public static void addToList(User currentUser, User displayedUser, boolean liked) {
        List<String> viewedList = currentUser.getViewed();
        List<String> likedList = currentUser.getLiked();
        viewedList.add(displayedUser.getUid());
        if (liked) {
            likedList.add(displayedUser.getUid());
        }
        UserRealtimeDbFacade.uploadUser(currentUser); // re-upload user to the database
    }
}
