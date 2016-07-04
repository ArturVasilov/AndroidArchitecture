package ru.itis.lectures.githubmvp.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import okhttp3.Interceptor;

import static org.junit.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class TokenInterceptorTest {

    private static final String TEST_TOKEN = "test_token";

    @Test
    public void testConstructor() throws Exception {
        Interceptor interceptor = TokenInterceptor.getInstance(TEST_TOKEN);
        assertNotNull(interceptor);
    }
}
