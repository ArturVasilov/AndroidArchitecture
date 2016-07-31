package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Badge;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class BadgeResponseTest {

    @Test
    public void testCreated() throws Exception {
        BadgeResponse badgeResponse = new BadgeResponse();
        assertNotNull(badgeResponse);
    }

    @Test
    public void testBadgesNotNull() throws Exception {
        BadgeResponse badgeResponse = new BadgeResponse();
        assertNotNull(badgeResponse.getBadges());
    }

    @Test
    public void testSetBadges() throws Exception {
        BadgeResponse badgeResponse = new BadgeResponse();
        List<Badge> badges = new ArrayList<>();
        badges.add(new Badge());
        badgeResponse.setBadges(badges);
        assertTrue(badges == badgeResponse.getBadges());
    }

}
