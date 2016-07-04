package ru.itis.lectures.githubmvp.content;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.itis.lectures.githubmvp.testutils.prefs.SharedPreferencesMapImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class SettingsTest {

    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = mock(Context.class);
        SharedPreferences preferences = new SharedPreferencesMapImpl();
        when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(preferences);
    }

    @Test
    public void testPutAndGetToken() throws Exception {
        String token = "some_test_token";
        Settings.putToken(mContext, token);
        assertEquals(token, Settings.getToken(mContext));
    }

    @Test
    public void testWalkthrough() throws Exception {
        assertFalse(Settings.isWalkthroughPassed(mContext));

        Settings.saveWalkthroughPassed(mContext);
        assertTrue(Settings.isWalkthroughPassed(mContext));
    }
}
