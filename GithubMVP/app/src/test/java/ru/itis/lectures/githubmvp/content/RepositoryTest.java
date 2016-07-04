package ru.itis.lectures.githubmvp.content;

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
public class RepositoryTest {

    public void testConstructor() throws Exception {
        Repository repository = new Repository();
        assertNotNull(repository);
    }

    @Test
    public void testFields() throws Exception {
        Repository repository = new Repository();

        assertNull(repository.getName());
        assertNull(repository.getDescription());
        assertNull(repository.getLanguage());
        assertEquals(0, repository.getStarsCount());
        assertEquals(0, repository.getForksCount());
        assertEquals(0, repository.getWatchersCount());

        repository.setName("AndroidTraining");
        repository.setDescription("Test Android repo");
        repository.setLanguage("Java");
        repository.setStarsCount(20);
        repository.setForksCount(3);
        repository.setWatchersCount(40);

        assertEquals("AndroidTraining", repository.getName());
        assertEquals("Test Android repo", repository.getDescription());
        assertEquals("Java", repository.getLanguage());
        assertEquals(20, repository.getStarsCount());
        assertEquals(3, repository.getForksCount());
        assertEquals(40, repository.getWatchersCount());
    }

    @Test
    public void testNullName() throws Exception {
        Repository repository = new Repository();
        repository.setName(null);
        assertEquals("", repository.getName());
    }

    @Test
    public void testNullDescription() throws Exception {
        Repository repository = new Repository();
        repository.setDescription(null);
        assertEquals("", repository.getDescription());
    }

    @Test
    public void testNullLanguage() throws Exception {
        Repository repository = new Repository();
        repository.setLanguage(null);
        assertEquals("", repository.getLanguage());
    }

    @Test
    public void testNegativeStars() throws Exception {
        Repository repository = new Repository();
        repository.setStarsCount(-5);
        assertEquals(0, repository.getStarsCount());

        repository.setStarsCount(-100000);
        assertEquals(0, repository.getStarsCount());
    }

    @Test
    public void testNegativeForks() throws Exception {
        Repository repository = new Repository();
        repository.setForksCount(-93);
        assertEquals(0, repository.getForksCount());

        repository.setForksCount(-1);
        assertEquals(0, repository.getForksCount());
    }

    @Test
    public void testNegativeWatchers() throws Exception {
        Repository repository = new Repository();
        repository.setWatchersCount(-10);
        assertEquals(0, repository.getWatchersCount());

        repository.setWatchersCount(-1);
        assertEquals(0, repository.getWatchersCount());
    }

}
