package ru.itis.lectures.githubmvp.content.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class AuthorizationTest {

    @Test
    public void testCreated() throws Exception {
        Authorization authorization = new Authorization();
        assertNotNull(authorization);
        assertNull(authorization.getToken());
        assertEquals(0, authorization.getId());
    }

    @Test
    public void testGettersSetters() throws Exception {
        Authorization authorization = new Authorization();

        authorization.setId(1121);
        assertEquals(1121, authorization.getId());

        String token = "test_token";
        authorization.setToken(token);
        assertEquals(token, authorization.getToken());
    }
}
