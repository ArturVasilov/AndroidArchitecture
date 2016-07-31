package ru.arturvasilov.stackexchangeclient.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.testutils.TestLocalRepository;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;
import ru.arturvasilov.stackexchangeclient.view.MainView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class MainPresenterTest {

    static {
        MockUtils.setupTestSchedulers();
    }

    private MainView mView;

    private MainPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(MainView.class);
        Mockito.doNothing().when(mView).showUserImage(anyString());
        Mockito.doNothing().when(mView).showUserName(anyString());
        Mockito.doNothing().when(mView).addTab(anyString());
        Mockito.doNothing().when(mView).showTags(anyListOf(String.class));
        Mockito.doNothing().when(mView).openProfile(any(User.class));
        Mockito.doNothing().when(mView).openAnswers(any(User.class));
        Mockito.doNothing().when(mView).hideTabLayout();

        Context context = MockUtils.mockContext();
        Mockito.when(context.getString(R.string.all)).thenReturn("All");
        Mockito.when(context.getString(R.string.my)).thenReturn("My");
        mPresenter = new MainPresenter(context, MockUtils.mockLoaderManager(), mView);

        RepositoryProvider.setRemoteRepository(new TestRemoteRepository());
        RepositoryProvider.setLocalRepository(new TestLocalRepository());
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testUserInfoShown() throws Exception {
        mPresenter.init();
        //2 - since we first load it from local db and then from network
        Mockito.verify(mView, times(2)).showUserImage("https://i.stack.imgur.com/EJNBv.jpg?s=512&g=1");
        Mockito.verify(mView, times(2)).showUserName("Artur Vasilov");
    }

    @Test
    public void testNoSavedTags() throws Exception {
        RepositoryProvider.setLocalRepository(new TagsRepository());

        mPresenter.init();
        Mockito.verify(mView).addTab("All");
        Mockito.verify(mView).addTab("My");
        Mockito.verify(mView).showTags(anyListOf(String.class));
    }

    @Test
    public void testMultipleTabs() throws Exception {
        List<String> tags = new ArrayList<>();
        tags.add("android");
        tags.add("java");
        RepositoryProvider.setLocalRepository(new TagsRepository(tags));

        mPresenter.init();
        Mockito.verify(mView).addTab("All");
        Mockito.verify(mView).addTab("My");
        Mockito.verify(mView).addTab("android");
        Mockito.verify(mView).addTab("java");
        Mockito.verify(mView).showTags(anyListOf(String.class));
    }

    @Test
    public void testNoTabs() throws Exception {
        mPresenter.init();
        Mockito.verify(mView).addTab("All");
        Mockito.verify(mView).hideTabLayout();
    }

    @Test
    public void testTabsUpdated() throws Exception {
        RepositoryProvider.setLocalRepository(new TagsRepository());
        mPresenter.init();
        Mockito.verify(mView).addTab("All");
        Mockito.verify(mView).addTab("My");

        List<String> tags = new ArrayList<>();
        tags.add("sql");
        RepositoryProvider.setLocalRepository(new TagsRepository(tags));

        mPresenter.onTagsResult();
        Mockito.verify(mView, times(2)).addTab("All");
        Mockito.verify(mView, times(2)).addTab("My");
        Mockito.verify(mView).addTab("sql");
        Mockito.verify(mView, times(2)).showTags(anyListOf(String.class));
    }

    @Test
    public void testOpenProfile() throws Exception {
        mPresenter.onProfileSelected();
        Mockito.verify(mView).openProfile(any(User.class));
    }

    @Test
    public void testOpenAnswers() throws Exception {
        mPresenter.onMyAnswersSelected();
        Mockito.verify(mView).openAnswers(any(User.class));
    }

    @Test
    public void testUpdateAfterTags() throws Exception {
        RepositoryProvider.setLocalRepository(new TagsRepository());
        mPresenter.init();

        mPresenter.onReturnFromTags();
        Mockito.verify(mView).clearTabs();
        Mockito.verify(mView, times(2)).showTags(anyListOf(String.class));
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setRemoteRepository(null);
        RepositoryProvider.setLocalRepository(null);
    }

    private class TagsRepository extends TestLocalRepository {

        private final List<String> mTags;

        public TagsRepository(@NonNull List<String> tags) {
            mTags = tags;
        }

        public TagsRepository() {
            mTags = new ArrayList<>();
        }

        @NonNull
        @Override
        public Observable<List<String>> tags() {
            return Observable.just(mTags);
        }

        @NonNull
        @Override
        public Observable<List<Question>> questions(@NonNull String tag) {
            List<Question> questions = new ArrayList<>();
            questions.add(new Question());
            return Observable.just(questions);
        }
    }
}
