package ru.arturvasilov.stackexchangeclient.model.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TagTest {

    @Test
    public void testCreated() throws Exception {
        Tag tag = new Tag("java", false);
        assertNotNull(tag);
    }

    @Test
    public void testTag() throws Exception {
        String tagSql = "sql";
        Tag tag = new Tag(tagSql, true);
        assertEquals(tagSql, tag.getName());
    }

    @Test
    public void testFavourite() throws Exception {
        Tag tag = new Tag("android-studio", true);
        assertTrue(tag.isFavourite());

        tag.setFavourite(false);
        assertFalse(tag.isFavourite());
    }
}
