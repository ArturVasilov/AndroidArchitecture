package ru.itis.lectures.githubmvp.activity;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.content.Settings;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class WalkthroughActivityTest {

    @Rule
    public final ActivityTestRule<WalkthroughActivity> mActivityRule = new ActivityTestRule<>(WalkthroughActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mActivityRule.getActivity());
    }

    //TODO : write your tests here


    @Test
    public void testStartsLogInActivity() throws Exception {
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withId(R.id.btn_walkthrough)).perform(click());

        Intents.intended(hasComponent(LogInActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        Settings.clear(mActivityRule.getActivity());
    }
}
