package com.group80.uoftinder;

/**
 * A public interface that is implemented by RecommendationView.
 */
public interface RecViewInterface {
    // set first displayed user
    void setDisplayedUser(User displayedUser);
    // display user
    void showUser(User displayedUser);
    // no more compatible users
    void noCompatibleUser();

}
