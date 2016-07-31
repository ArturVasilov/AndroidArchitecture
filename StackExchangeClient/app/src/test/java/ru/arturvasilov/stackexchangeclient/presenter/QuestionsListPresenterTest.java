package ru.arturvasilov.stackexchangeclient.presenter;

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
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.testutils.TestLocalRepository;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;
import ru.arturvasilov.stackexchangeclient.view.QuestionsListView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class QuestionsListPresenterTest {

    private QuestionsListView mView;

    private QuestionsListPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(QuestionsListView.class);
        Mockito.doNothing().when(mView).showQuestions(anyListOf(Question.class));
        Mockito.doNothing().when(mView).addQuestions(anyListOf(Question.class));
        Mockito.doNothing().when(mView).hideRefresh();
        Mockito.doNothing().when(mView).showEmptyListView();
        Mockito.doNothing().when(mView).hideEmptyListView();

        mPresenter = new QuestionsListPresenter(MockUtils.mockContext(), MockUtils.mockLoaderManager(),
                mView, "android");

        RepositoryProvider.setRemoteRepository(new TestRemoteRepository());
        RepositoryProvider.setLocalRepository(new TestLocalRepository());
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testQuestionsShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRemoteRepository());
        RepositoryProvider.setLocalRepository(new ContentLocalRepository());

        mPresenter.init();
        Mockito.verify(mView, times(2)).showQuestions(anyListOf(Question.class));
        Mockito.verify(mView, times(2)).hideEmptyListView();
    }

    @Test
    public void testRefresh() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRemoteRepository());
        RepositoryProvider.setLocalRepository(new ContentLocalRepository());
        mPresenter.init();

        mPresenter.refresh();
        Mockito.verify(mView, times(3)).showQuestions(anyListOf(Question.class));
        Mockito.verify(mView, times(3)).hideEmptyListView();
        Mockito.verify(mView).hideRefresh();
    }

    @Test
    public void testShowError() throws Exception {
        mPresenter.init();
        Mockito.verify(mView, times(2)).showEmptyListView();
    }

    @Test
    public void testHandleError() throws Exception {
        RepositoryProvider.setRemoteRepository(new ErrorRemoteRepository());
        mPresenter.init();

        Mockito.verify(mView, Mockito.times(2)).showEmptyListView();
        Mockito.verify(mView).hideRefresh();
    }

    @Test
    public void testErrorIgnored() throws Exception {
        RepositoryProvider.setRemoteRepository(new ErrorRemoteRepository());
        RepositoryProvider.setLocalRepository(new ContentLocalRepository());
        mPresenter.init();

        Mockito.verify(mView, never()).showEmptyListView();
        Mockito.verify(mView).hideRefresh();
    }

    @Test
    public void testScrollMore() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRemoteRepository());
        RepositoryProvider.setLocalRepository(new ContentLocalRepository());
        mPresenter.init();

        mPresenter.onScrolled(45);
        Mockito.verify(mView).addQuestions(anyListOf(Question.class));
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setRemoteRepository(null);
        RepositoryProvider.setLocalRepository(null);
    }

    @NonNull
    private Observable<List<Question>> testQuestions() {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            questions.add(new Question());
        }
        return Observable.just(questions);
    }

    private class ContentRemoteRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Question>> questions(@NonNull String tag) {
            return testQuestions();
        }

        @NonNull
        @Override
        public Observable<List<Question>> moreQuestions(@NonNull String tag, long toDate) {
            return testQuestions();
        }
    }

    private class ContentLocalRepository extends TestLocalRepository {

        @NonNull
        @Override
        public Observable<List<Question>> questions(@NonNull String tag) {
            return testQuestions();
        }
    }

    private class ErrorRemoteRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Question>> questions(@NonNull String tag) {
            return Observable.error(new IOException());
        }

        @NonNull
        @Override
        public Observable<List<Question>> moreQuestions(@NonNull String tag, long toDate) {
            return Observable.error(new IOException());
        }

    }
}
