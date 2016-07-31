package ru.arturvasilov.stackexchangeclient.model.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class BadgeTest {

    @Test
    public void testCreated() throws Exception {
        Badge badge = new Badge();
        assertNotNull(badge);
    }

    @Test
    public void testName() throws Exception {
        Badge badge = new Badge();
        String name = "name";
        badge.setName(name);
        assertEquals(name, badge.getName());
    }

    @Test
    public void testLink() throws Exception {
        Badge badge = new Badge();
        String link = "http://stackoverflow.com/help/badges/13/yearling";
        badge.setLink(link);
        assertEquals(link, badge.getLink());
    }

    @Test
    public void testAll() throws Exception {
        Badge badge = new Badge();
        String name = "badge_name";
        String link = "http://stackoverflow.com/help/badges/23/nice-answer";

        badge.setName(name);
        badge.setLink(link);

        assertEquals(name, badge.getName());
        assertEquals(link, badge.getLink());
    }
}
