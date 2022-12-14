package com.group80.uoftinder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.firebase.auth.FirebaseAuth;
import com.group80.uoftinder.view_layer.login.LoginActivity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * A ContactActivityUITest that tests the activity_contacts view.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ContactActivityUITest {
    private static String inputEmail;
    private static String inputPassword;
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Initialize the expectedEmail and expectedPassword
     */
    @BeforeClass
    public static void setUp() {
        inputEmail = "csc207.group80.uoftinder.bot2@gmail.com";
        inputPassword = "000000";
    }

    /**
     * Logs that the ContactActivityUITest is over.
     */
    @AfterClass
    public static void tearDown() {
        Log.i("ContactActivityUITest", "Done test");
    }

    @Before
    public void login() {
        onView(withId(R.id.loginEmail)).perform(replaceText(inputEmail), closeSoftKeyboard());
        onView(withId(R.id.loginPassword)).perform(replaceText(inputPassword), closeSoftKeyboard());
        onView(withId(R.id.EnterLogin)).perform(click());
    }

    @After
    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    /**
     * Test that the program switches to the contact page after clicking the 'Enter Chat' button
     * on the filter page and that the right contacts are displayed.
     */
    @Test
    public void testSwitchContactPage() {
        onView(withId(R.id.chatButton)).perform(click());
        onView(withText("Grad Student")).check(matches(isDisplayed()));
    }

    /**
     * Test that the program switches back to the recommendation view after clicking the back button
     * in the contact view.
     */
    @Test
    public void testBackButton() {
        onView(withId(R.id.chatButton)).perform(click());
        onView(withId(R.id.contactsActivityBackButton)).perform(click());

        onView(withId(R.id.logoutButton)).check(matches(isDisplayed()));
    }
}