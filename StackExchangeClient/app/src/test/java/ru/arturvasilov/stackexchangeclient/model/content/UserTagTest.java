package ru.arturvasilov.stackexchangeclient.model.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class UserTagTest {

    @Test
    public void testCreated() throws Exception {
        UserTag userTag = new UserTag();
        assertNotNull(userTag);
    }

    @Test
    public void testName() throws Exception {
        UserTag userTag = new UserTag();
        String tagName = "android-layout";
        userTag.setTagName(tagName);
        assertEquals(tagName, userTag.getTagName());
    }

    @Test
    public void testQuestionCount() throws Exception {
        UserTag userTag = new UserTag();
        int questionsCount = 32781;
        userTag.setQuestionCount(questionsCount);
        assertEquals(questionsCount, userTag.getQuestionCount());
    }

    @Test
    public void testAnswerCount() throws Exception {
        UserTag userTag = new UserTag();
        int answersCount = 18212;
        userTag.setAnswerCount(answersCount);
        assertEquals(answersCount, userTag.getAnswerCount());
    }

    @Test
    public void testAll() throws Exception {
        UserTag userTag = new UserTag();
        String tagName = "android-google-maps";
        int questionsCount = 7484;
        int answersCount = 4891;

        userTag.setTagName(tagName);
        userTag.setQuestionCount(questionsCount);
        userTag.setAnswerCount(answersCount);

        assertEquals(tagName, userTag.getTagName());
        assertEquals(questionsCount, userTag.getQuestionCount());
        assertEquals(answersCount, userTag.getAnswerCount());
    }
}
