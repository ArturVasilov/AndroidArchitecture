package ru.itis.lectures.githubmvp.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class DefaultApiProviderTest {

    @Test
    public void testConstructor() throws Exception {
        ApiProvider provider = new DefaultApiProvider("aaa");
        assertNotNull(provider);
    }

    @Test
    public void testToken() throws Exception {
        ApiProvider provider = new DefaultApiProvider("bbb");
        assertEquals("bbb", provider.provideToken());
    }

    @Test
    public void testGithubService() throws Exception {
        ApiProvider provider = new DefaultApiProvider("cc");
        assertNotNull(provider.provideGithubService());
    }
}
