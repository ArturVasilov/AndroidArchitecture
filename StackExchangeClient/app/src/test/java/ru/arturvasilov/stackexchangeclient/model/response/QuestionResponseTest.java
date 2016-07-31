package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Question;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class QuestionResponseTest {

    @Test
    public void testCreated() throws Exception {
        QuestionResponse questionResponse = new QuestionResponse();
        assertNotNull(questionResponse);
    }

    @Test
    public void testQuestionsNotNull() throws Exception {
        QuestionResponse questionResponse = new QuestionResponse();
        assertNotNull(questionResponse.getQuestions());
    }

    @Test
    public void testSetQuestions() throws Exception {
        QuestionResponse questionResponse = new QuestionResponse();
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        questionResponse.setQuestions(questions);
        assertTrue(questions == questionResponse.getQuestions());
    }

}
