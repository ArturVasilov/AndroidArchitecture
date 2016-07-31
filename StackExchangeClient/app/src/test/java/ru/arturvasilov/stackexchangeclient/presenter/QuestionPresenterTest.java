package ru.arturvasilov.stackexchangeclient.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import ru.arturvasilov.stackexchangeclient.view.QuestionView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class QuestionPresenterTest {

    private static final String ERROR_SHOWN_KEY = "error_shown";

    private QuestionView mView;

    private LoadingView mLoadingView;
    private ErrorView mErrorView;

    private QuestionPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(QuestionView.class);
        Mockito.doNothing().when(mView).showQuestion(any(Question.class));
        Mockito.doNothing().when(mView).showAnswers(anyListOf(Answer.class));

        mLoadingView = MockUtils.mockLoadingView();
        mErrorView = MockUtils.mockErrorView();

        Question question = new Question();
        question.setQuestionId(111);

        mPresenter = new QuestionPresenter(MockUtils.mockContext(), MockUtils.mockLoaderManager(),
                mView, mLoadingView, mErrorView, question);

        RepositoryProvider.setRemoteRepository(new TestRemoteRepository());
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testLoadingProgressShown() throws Exception {
        mPresenter.init(null);

        Mockito.verify(mLoadingView).showLoadingIndicator();
        Mockito.verify(mLoadingView).hideLoadingIndicator();
    }

    @Test
    public void testQuestionShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRepository());
        mPresenter.init(null);

        Mockito.verify(mView).showQuestion(any(Question.class));
        Mockito.verify(mView).showAnswers(anyListOf(Answer.class));
    }

    @Test
    public void testErrorShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ErrorRepository());
        mPresenter.init(null);

        Mockito.verify(mErrorView).showUnexpectedError();
    }

    @Test
    public void testErrorShownOnlyOnce() throws Exception {
        Bundle savedInstanceState = Mockito.mock(Bundle.class);
        Mockito.when(savedInstanceState.getBoolean(ERROR_SHOWN_KEY)).thenReturn(true);

        RepositoryProvider.setRemoteRepository(new ErrorRepository());
        mPresenter.init(savedInstanceState);

        Mockito.verifyNoMoreInteractions(mErrorView);
    }

    @Test
    public void testErrorStateSaved() throws Exception {
        Bundle outState = Mockito.mock(Bundle.class);
        Mockito.doNothing().when(outState).putBoolean(eq(ERROR_SHOWN_KEY), anyBoolean());
        RepositoryProvider.setRemoteRepository(new ErrorRepository());

        mPresenter.init(null);
        mPresenter.onSaveInstanceState(outState);

        Mockito.verify(outState).putBoolean(ERROR_SHOWN_KEY, true);
    }

    @Test
    public void testQuestionShownEvenWithError() throws Exception {
        mPresenter.init(null);
        Mockito.verify(mView).showQuestion(any(Question.class));
        Mockito.verify(mView, never()).showAnswers(anyListOf(Answer.class));
    }

    @After
    public void tearDown() throws Exception {
        //noinspection ConstantConditions
        RepositoryProvider.setRemoteRepository(null);
    }

    private class ContentRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<Question> questionWithBody(int questionId) {
            Question question = new Question();
            question.setQuestionId(1111);
            question.setTitle("Question");
            question.setBody("Body");
            question.setAnswerCount(1);
            question.setViewCount(190);
            User user = new User();
            user.setName("John");
            question.setOwner(user);
            return Observable.just(question);
        }

        @NonNull
        @Override
        public Observable<List<Answer>> questionAnswers(int questionId) {
            List<Answer> answers = new ArrayList<>();
            answers.add(new Answer());
            answers.add(new Answer());
            return Observable.just(answers);
        }
    }

    private class ErrorRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<Question> questionWithBody(int questionId) {
            return Observable.error(new IOException());
        }

        @NonNull
        @Override
        public Observable<List<Answer>> questionAnswers(int questionId) {
            return Observable.error(new IOException());
        }

    }
}
