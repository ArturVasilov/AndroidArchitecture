package ru.arturvasilov.stackexchangeclient.model.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.arturvasilov.stackexchangeclient.api.ApiConstants;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class QuestionTest {

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
        Question question = new Question();
        assertNotNull(question);
    }

    @Test
    public void testQuestionId() throws Exception {
        Question question = new Question();
        int questionId = 10031391;
        question.setQuestionId(questionId);
        assertEquals(questionId, question.getQuestionId());
    }

    @Test
    public void testTitle() throws Exception {
        Question question = new Question();
        String title = "test title";
        question.setTitle(title);
        assertEquals(title, question.getTitle());
    }

    @Test
    public void testBody() throws Exception {
        Question question = new Question();
        String body = "test body";
        question.setBody(body);
        assertEquals(body, question.getBody());
    }

    @Test
    public void testLink() throws Exception {
        Question question = new Question();
        String link = "http://stackoverflow.com/questions/23658561/" +
                "how-to-set-my-own-icon-for-markers-in-clusterer-in-google-maps/23684039#23684039";
        question.setLink(link);
        assertEquals(link, question.getLink());
    }

    @Test
    public void testOwner() throws Exception {
        Question question = new Question();
        question.setOwner(mOwner);
        assertTrue(mOwner == question.getOwner());
    }

    @Test
    public void testAnswered() throws Exception {
        Question question = new Question();
        question.setAnswered(true);
        assertTrue(question.isAnswered());
    }

    @Test
    public void testViewCount() throws Exception {
        Question question = new Question();
        int viewCount = 372;
        question.setViewCount(viewCount);
        assertEquals(viewCount, question.getViewCount());
    }

    @Test
    public void testAnswerCount() throws Exception {
        Question question = new Question();
        int answerCount = 2;
        question.setAnswerCount(answerCount);
        assertEquals(2, question.getAnswerCount());
    }

    @Test
    public void testCreationDate() throws Exception {
        Question question = new Question();
        long creationDate = System.currentTimeMillis() / 1000;
        question.setCreationDate(creationDate);
        assertEquals(creationDate, question.getCreationDate());
    }

    @Test
    public void testTag() throws Exception {
        Question question = new Question();
        String tag = "android";
        question.setTag(tag);
        assertEquals(tag, question.getTag());
    }

    @Test
    public void testEmptyTag() throws Exception {
        Question question = new Question();
        question.setTag("");
        assertEquals(ApiConstants.TAG_ALL, question.getTag());
    }

    @Test
    public void testAll() throws Exception {
        Question question = new Question();
        int questionId = 3;
        String title = "";
        String body = "asx][sx'a;.lcnsdc;'dcscd";
        String link = "http://stackoverflow.com/questions/27745299/" +
                "how-to-add-title-snippet-and-icon-to-clusteritem/27745681#27745681";
        int viewCount = 3;
        int answerCount = 0;
        String tag = "android-studio";

        question.setQuestionId(questionId);
        question.setTitle(title);
        question.setBody(body);
        question.setLink(link);
        question.setOwner(mOwner);
        question.setAnswered(false);
        question.setViewCount(viewCount);
        question.setAnswerCount(answerCount);
        question.setTag(tag);

        assertEquals(questionId, question.getQuestionId());
        assertEquals(title, question.getTitle());
        assertEquals(body, question.getBody());
        assertEquals(link, question.getLink());
        assertTrue(mOwner == question.getOwner());
        assertFalse(question.isAnswered());
        assertEquals(viewCount, question.getViewCount());
        assertEquals(answerCount, question.getAnswerCount());
        assertEquals(tag, question.getTag());
    }
}
