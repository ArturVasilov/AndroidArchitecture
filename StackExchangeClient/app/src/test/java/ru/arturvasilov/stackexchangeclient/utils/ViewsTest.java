package ru.arturvasilov.stackexchangeclient.utils;

import android.app.Activity;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.arturvasilov.stackexchangeclient.R;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Artur
 */
@RunWith(JUnit4.class)
public class ViewsTest {

    private static final int TEST_VIEW_ID = R.id.toolbar;

    @Test
    public void testFindByIdInActivity() throws Exception {
        Activity activity = mock(Activity.class);
        View view = mock(View.class);
        when(activity.findViewById(TEST_VIEW_ID)).thenReturn(view);

        assertTrue(view == Views.findById(activity, TEST_VIEW_ID));
        verify(activity).findViewById(TEST_VIEW_ID);
    }

    @Test
    public void testFindByIdView() throws Exception {
        View findView = mock(View.class);
        View view = mock(View.class);
        when(findView.findViewById(TEST_VIEW_ID)).thenReturn(view);

        assertTrue(view == Views.findById(findView, TEST_VIEW_ID));
        verify(findView).findViewById(TEST_VIEW_ID);
    }
}
