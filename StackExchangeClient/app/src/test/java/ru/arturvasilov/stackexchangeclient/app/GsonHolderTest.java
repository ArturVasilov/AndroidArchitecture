package ru.arturvasilov.stackexchangeclient.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class GsonHolderTest {

    @Test
    public void testGsonNotNull() throws Exception {
        assertNotNull(GsonHolder.getGson());
    }
}
