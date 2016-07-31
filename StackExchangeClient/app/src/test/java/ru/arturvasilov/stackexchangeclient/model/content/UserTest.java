package ru.arturvasilov.stackexchangeclient.model.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.arturvasilov.stackexchangeclient.model.content.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class UserTest {

    @Test
    public void testCreated() throws Exception {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testUserId() throws Exception {
        int id = 932123;
        User user = new User();
        user.setUserId(id);
        assertEquals(id, user.getUserId());
    }

    @Test
    public void testAge() throws Exception {
        int age = 19;
        User user = new User();
        user.setAge(age);
        assertEquals(age, user.getAge());
    }

    @Test
    public void testName() throws Exception {
        String name = "John";
        User user = new User();
        user.setName(name);
        assertEquals(name, user.getName());
    }

    @Test
    public void testReputation() throws Exception {
        int reputation = 2985;
        User user = new User();
        user.setReputation(reputation);
        assertEquals(reputation, user.getReputation());
    }

    @Test
    public void testLink() throws Exception {
        String link = "http://stackoverflow.com/users/3637200/vasilov-artur";
        User user = new User();
        user.setLink(link);
        assertEquals(link, user.getLink());
    }

    @Test
    public void testImageUrl() throws Exception {
        String imageUrl = "https://i.stack.imgur.com/EJNBv.jpg?s=512&g=1";
        User user = new User();
        user.setProfileImage(imageUrl);
        assertEquals(imageUrl, user.getProfileImage());
    }

    @Test
    public void testAll() throws Exception {
        int id = 932123;
        int age = 19;
        String name = "John";
        int reputation = 2985;
        String link = "http://stackoverflow.com/users/3637200/vasilov-artur";
        String imageUrl = "https://i.stack.imgur.com/EJNBv.jpg?s=512&g=1";

        User user = new User();
        user.setUserId(id);
        user.setAge(age);
        user.setName(name);
        user.setReputation(reputation);
        user.setLink(link);
        user.setProfileImage(imageUrl);

        assertEquals(id, user.getUserId());
        assertEquals(age, user.getAge());
        assertEquals(name, user.getName());
        assertEquals(reputation, user.getReputation());
        assertEquals(link, user.getLink());
        assertEquals(imageUrl, user.getProfileImage());
    }
}
