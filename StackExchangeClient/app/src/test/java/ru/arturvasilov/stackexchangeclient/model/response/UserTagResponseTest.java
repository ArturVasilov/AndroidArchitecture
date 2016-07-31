package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.UserTag;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class UserTagResponseTest {

    @Test
    public void testCreated() throws Exception {
        UserTagResponse userTagResponse = new UserTagResponse();
        assertNotNull(userTagResponse);
    }

    @Test
    public void testUserTagsNotNull() throws Exception {
        UserTagResponse userTagResponse = new UserTagResponse();
        assertNotNull(userTagResponse.getUserTags());
    }

    @Test
    public void testSetUserTags() throws Exception {
        UserTagResponse userTagResponse = new UserTagResponse();
        List<UserTag> userTags = new ArrayList<>();
        userTags.add(new UserTag());
        userTagResponse.setUserTags(userTags);
        assertTrue(userTags == userTagResponse.getUserTags());
    }

}
