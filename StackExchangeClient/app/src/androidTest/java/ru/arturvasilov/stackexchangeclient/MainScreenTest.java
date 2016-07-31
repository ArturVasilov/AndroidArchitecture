package ru.arturvasilov.stackexchangeclient;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.arturvasilov.stackexchangeclient.activity.MainActivity;

import static org.junit.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class MainScreenTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testStarted() throws Exception {
        assertNotNull(mActivityRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
