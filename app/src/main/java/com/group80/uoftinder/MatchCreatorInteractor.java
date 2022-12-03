package com.group80.uoftinder;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.group80.uoftinder.chat.MessageFactory;
import com.group80.uoftinder.entities.User;
import com.group80.uoftinder.firebase.realtime.UserRealtimeDbFacade;

import com.group80.uoftinder.firebase.realtime.*;

import java.util.List;

/**
 * The MatchCreatorInteractor class is used to create a match between the current user and the user
 * they have liked, if that user has also liked them
 */
public class MatchCreatorInteractor {
//    private final User currentUser;
//    private final User user2;

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
    // called when currentUser likes user2
    public static boolean checkForMatchAndCreate(User currentUser, User user2) {
        if (user2.getLiked().contains(currentUser.getUid())) { // if user2 has liked currentUser
            List<String> user1MatchList = currentUser.getMatches();
            List<String> user2MatchList = user2.getMatches();
            user1MatchList.add(user2.getUid()); // add both users to each other's match lists
            user2MatchList.add(currentUser.getUid());

            UserRealtimeDbFacade.uploadUser(user2); // re-upload users to the database
            UserRealtimeDbFacade.uploadUser(currentUser);

            sendIntroMessage(currentUser.getUid(), user2.getUid(), "Hey! We matched with each other!"); // send a message from currentUser to user2
            return true;
        }
        return false;
    }

    private static void sendIntroMessage(String selfUid, String contactUid, String message) {
        String chatRoom = selfUid.compareTo(contactUid) < 0 ? selfUid + contactUid : contactUid + selfUid;

        ucChatMessageWriter chatMessageWriter = new ucChatMessageWriter(new RealtimeDbValueObserver() {
            @Override
            public void onRealtimeDbDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onRealtimeDbCancelled(@NonNull DatabaseError error) {
            }
        }, chatRoom);

        chatMessageWriter.write(MessageFactory.createMessage(message, selfUid));
    }
}
