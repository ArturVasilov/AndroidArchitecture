package ru.arturvasilov.stackexchangeclient.model.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class AnswerTest {

    private User mOwner;

    @Before
    public void setUp() throws Exception {
        mOwner = new User();
        mOwner.setUserId(932123);
        mOwner.setAge(19);
        mOwner.setName("John");
        mOwner.setReputation(2985);
        mOwner.setLink("http://stackoverflow.com/users/3637200/vasilov-artur");
        mOwner.setProfileImage("https://i.stack.imgur.com/EJNBv.jpg?s=512&g=1");
    }

    @Test
    public void testCreated() throws Exception {
        Answer answer = new Answer();
        assertNotNull(answer);
    }

    @Test
    public void testOwner() throws Exception {
        Answer answer = new Answer();
        answer.setOwner(mOwner);
        assertTrue(mOwner == answer.getOwner());
    }

    @Test
    public void testBody() throws Exception {
        Answer answer = new Answer();
        String body = "body";
        answer.setBody(body);
        assertEquals(body, answer.getBody());
    }

    @Test
    public void testIsAccepted() throws Exception {
        Answer answer = new Answer();
        answer.setAccepted(true);
        assertTrue(answer.isAccepted());
    }

    @Test
    public void testQuestionId() throws Exception {
        Answer answer = new Answer();
        int questionId = 1410413;
        answer.setQuestionId(questionId);
        assertEquals(questionId, answer.getQuestionId());
    }

    @Test
    public void testAnswerId() throws Exception {
        Answer answer = new Answer();
        int answerId = 4138013;
        answer.setAnswerId(answerId);
        assertEquals(answerId, answer.getAnswerId());
    }

    @Test
    public void testAll() throws Exception {
        Answer answer = new Answer();
        String body = "body";
        int questionId = 2;
        int answerId = 1213123129;

        answer.setOwner(mOwner);
        answer.setBody(body);
        answer.setAccepted(false);
        answer.setQuestionId(questionId);
        answer.setAnswerId(answerId);

        assertTrue(mOwner == answer.getOwner());
        assertEquals(body, answer.getBody());
        assertFalse(answer.isAccepted());
        assertEquals(questionId, answer.getQuestionId());
        assertEquals(answerId, answer.getAnswerId());
    }
}
