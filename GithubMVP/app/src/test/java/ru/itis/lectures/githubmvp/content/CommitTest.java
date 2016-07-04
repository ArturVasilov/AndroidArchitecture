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
public class CommitTest {

    public void testEmptyConstructor() throws Exception {
        Commit commit = new Commit();
        assertNotNull(commit);
    }

    public void testConstructor() throws Exception {
        Commit commit = new Commit("repoName", "author", "message");
        assertNotNull(commit);
    }

    @Test
    public void testNullFields() throws Exception {
        Commit commit = new Commit();
        assertNull(commit.getRepoName());
        assertNull(commit.getAuthor());
        assertNull(commit.getMessage());

        commit = new Commit("repoName", "author", "message");
        assertEquals("repoName", commit.getRepoName());
        assertEquals("author", commit.getAuthor());
        assertEquals("message", commit.getMessage());
    }

}
