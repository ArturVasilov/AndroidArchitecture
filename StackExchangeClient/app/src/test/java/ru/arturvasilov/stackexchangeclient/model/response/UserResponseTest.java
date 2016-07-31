package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.User;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class UserResponseTest {

    @Test
    public void testCreated() throws Exception {
        UserResponse userResponse = new UserResponse();
        assertNotNull(userResponse);
    }

    @Test
    public void testUsersNotNull() throws Exception {
        UserResponse userResponse = new UserResponse();
        assertNotNull(userResponse.getUsers());
    }

    @Test
    public void testSetUsers() throws Exception {
        UserResponse userResponse = new UserResponse();
        List<User> users = new ArrayList<>();
        users.add(new User());
        userResponse.setUsers(users);
        assertTrue(users == userResponse.getUsers());
    }
}
