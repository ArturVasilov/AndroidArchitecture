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
public class NotificationTest {

    @Test
    public void testCreated() throws Exception {
        Notification notification = new Notification();
        assertNotNull(notification);
    }

    @Test
    public void testUnread() throws Exception {
        Notification notification = new Notification();
        notification.setUnread(true);
        assertTrue(notification.isUnread());
    }

    @Test
    public void testCreationDate() throws Exception {
        Notification notification = new Notification();
        long creationDate = System.currentTimeMillis();
        notification.setCreationDate(creationDate);
        assertEquals(creationDate, notification.getCreationDate());
    }

    @Test
    public void testBody() throws Exception {
        Notification notification = new Notification();
        String body = "body";
        notification.setBody(body);
        assertEquals(body, notification.getBody());
    }

    @Test
    public void testAll() throws Exception {
        Notification notification = new Notification();
        long creationDate = System.currentTimeMillis();
        String body = "body";

        notification.setUnread(false);
        notification.setCreationDate(creationDate);
        notification.setBody(body);

        assertFalse(notification.isUnread());
        assertEquals(creationDate, notification.getCreationDate());
        assertEquals(body, notification.getBody());
    }
}
