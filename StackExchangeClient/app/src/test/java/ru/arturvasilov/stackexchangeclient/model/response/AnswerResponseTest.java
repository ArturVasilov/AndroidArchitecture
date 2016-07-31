package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Answer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class AnswerResponseTest {

    @Test
    public void testCreated() throws Exception {
        AnswerResponse answerResponse = new AnswerResponse();
        assertNotNull(answerResponse);
    }

    @Test
    public void testAnswersNotNull() throws Exception {
        AnswerResponse answerResponse = new AnswerResponse();
        assertNotNull(answerResponse.getAnswers());
    }

    @Test
    public void testSetAnswers() throws Exception {
        AnswerResponse answerResponse = new AnswerResponse();
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer());
        answerResponse.setAnswers(answers);
        assertTrue(answers == answerResponse.getAnswers());
    }

}
