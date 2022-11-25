package com.group80.uoftinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.group80.uoftinder.feed.RecommendationPresenter;
import com.group80.uoftinder.feed.RecommendationView;
import com.group80.uoftinder.entities.User;

import org.junit.Test;

/**
 * A RecommendationPresenterUnitTest class that tests the functionality of the
 * RecommendationPresenter class
 */
public class RecommendationPresenterUnitTest {

    /**
     * Test that RecommendationPresenter.displayMostCompUser changes the displayedUser attribute
     * in RecommendationPresenter.recViewInterface
     */
    @Test
    public void displayMostCompUserTest() {
        User curUser = new User("curUser");
        RecommendationView recView = new RecommendationView(curUser);
        RecommendationPresenter recPresenter = new RecommendationPresenter(recView);
        User user2 = new User("user2");
        recView.setDisplayedUser(null);
        recPresenter.displayMostCompUser(user2);
        User actual = recView.getDisplayedUser();
        assertEquals(actual, user2);
    }

    /**
     * Test that RecommendationPresenter.displayNoCompatibleUser changes the displayedUser attribute
     * in RecommendationPresenter.recViewInterface to null
     */
    @Test
    public void displayNoCompatibleUserTest() {
        User curUser = new User("curUser");
        RecommendationView recView = new RecommendationView(curUser);
        RecommendationPresenter recPresenter = new RecommendationPresenter(recView);
        recPresenter.displayNoCompatibleUser();
        User actual = recView.getDisplayedUser();
        assertNull(actual);
    }
}