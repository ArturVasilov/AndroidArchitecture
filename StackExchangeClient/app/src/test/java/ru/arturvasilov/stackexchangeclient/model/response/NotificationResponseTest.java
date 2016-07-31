package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Notification;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class NotificationResponseTest {

    @Test
    public void testCreated() throws Exception {
        NotificationResponse notificationResponse = new NotificationResponse();
        assertNotNull(notificationResponse);
    }

    @Test
    public void testNotificationsNotNull() throws Exception {
        NotificationResponse notificationResponse = new NotificationResponse();
        assertNotNull(notificationResponse.getNotifications());
    }

    @Test
    public void testSetNotifications() throws Exception {
        NotificationResponse notificationResponse = new NotificationResponse();
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification());
        notificationResponse.setNotifications(notifications);
        assertTrue(notifications == notificationResponse.getNotifications());
    }

}
