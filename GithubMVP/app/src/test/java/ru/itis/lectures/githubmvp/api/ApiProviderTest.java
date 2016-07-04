package ru.itis.lectures.githubmvp.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.itis.lectures.githubmvp.testutils.TestGithubService;
import ru.itis.lectures.githubmvp.testutils.TestProviderImpl;

import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class ApiProviderTest {

    @Before
    public void setUp() throws Exception {
        ApiFactory.resetProvider();
    }

    @Test
    public void testSetAndGetProvider() throws Exception {
        ApiProvider provider = new TestProviderImpl(new TestGithubService());
        ApiFactory.setProvider(provider);

        assertTrue(provider == ApiFactory.getProvider());
    }

}
