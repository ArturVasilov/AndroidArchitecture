package ru.itis.lectures.githubmvp.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TextUtilsTest {

    @Test
    public void testNullString() throws Exception {
        assertTrue(TextUtils.isEmpty(null));
    }

    @Test
    public void testEmptyString() throws Exception {
        assertTrue(TextUtils.isEmpty(""));
    }

    @Test
    public void testNotEmptyString() throws Exception {
        assertFalse(TextUtils.isEmpty("not_empty_string"));
    }
}
