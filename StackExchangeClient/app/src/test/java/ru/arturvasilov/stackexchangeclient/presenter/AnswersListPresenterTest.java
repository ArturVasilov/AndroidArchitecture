package ru.arturvasilov.stackexchangeclient.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;
import ru.arturvasilov.stackexchangeclient.view.AnswersListView;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class AnswersListPresenterTest {

    private static final String ERROR_SHOWN_KEY = "error_shown";

    private AnswersListView mView;
    private LoadingView mLoadingView;
    private ErrorView mErrorView;

    private AnswersListPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(AnswersListView.class);
        Mockito.doNothing().when(mView).showAnswers(anyListOf(Answer.class));
        Mockito.doNothing().when(mView).setEmptyText(anyInt());
        Mockito.doNothing().when(mView).browseUrl(anyString());
        Mockito.doNothing().when(mView).showEmptyListView();
        Mockito.doNothing().when(mView).hideEmptyListView();

        mLoadingView = MockUtils.mockLoadingView();
        mErrorView = MockUtils.mockErrorView();

        User user = new User();
        user.setUserId(932123);
        user.setAge(19);
        user.setName("Artur Vasilov");
        user.setReputation(2985);
        user.setLink("http://stackoverflow.com/users/3637200/vasilov-artur");
        user.setProfileImage("https://i.stack.imgur.com/EJNBv.jpg?sz=128&g=1");

        mPresenter = new AnswersListPresenter(MockUtils.mockContext(), MockUtils.mockLoaderManager(), mView,
                mLoadingView, mErrorView, user);

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
    public void testShowAnswers() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRepository());
        mPresenter.init(null);

        Mockito.verify(mView).setEmptyText(R.string.no_answers);
        Mockito.verify(mView).showAnswers(anyListOf(Answer.class));
        Mockito.verify(mView).hideEmptyListView();
    }

    @Test
    public void testOnItemClick() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRepository());
        mPresenter.init(null);
        Answer answer = new Answer();
        answer.setQuestionId(111);
        answer.setAnswerId(222);
        mPresenter.onItemClick(answer);

        Mockito.verify(mView).browseUrl("http://stackoverflow.com/a/111/222");
    }

    @Test
    public void testErrorShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ErrorRepository());
        mPresenter.init(null);

        Mockito.verify(mErrorView).showNetworkError();
        Mockito.verify(mView).showEmptyListView();
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

    @After
    public void tearDown() throws Exception {
        //noinspection ConstantConditions
        RepositoryProvider.setRemoteRepository(null);
    }

    private class ContentRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Answer>> answers(int userId) {
            List<Answer> answers = new ArrayList<>();
            answers.add(new Answer());
            return Observable.just(answers);
        }
    }

    private class ErrorRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Answer>> answers(int userId) {
            return Observable.error(new UnknownHostException());
        }
    }
}
