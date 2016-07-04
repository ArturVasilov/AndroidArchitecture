package ru.itis.lectures.githubmvp.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.repository.RepositoriesRepository;
import ru.itis.lectures.githubmvp.rx.RxUtils;
import ru.itis.lectures.githubmvp.testutils.TestGithubService;
import ru.itis.lectures.githubmvp.testutils.TestProviderImpl;
import ru.itis.lectures.githubmvp.view.RepositoriesView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Artur Vasilov
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class RepositoriesPresenterTest {

    private RepositoriesView mView;
    private RepositoriesPresenter mPresenter;

    static {
        RxUtils.setupTestSchedulers();
    }

    @Before
    public void setUp() throws Exception {
        Context context = RxUtils.rxContext();
        LoaderManager loaderManager = RxUtils.rxLoaderManager();

        mView = mock(RepositoriesView.class);
        doNothing().when(mView).showLoading();
        doNothing().when(mView).hideLoading();
        doNothing().when(mView).showError();
        doNothing().when(mView).showRepositories(anyList());
        doNothing().when(mView).showCommits(any(Repository.class));

        RepositoriesRepository repository = new TestRepositoryImpl(
                generateRepositories(15));
        mPresenter = new RepositoriesPresenter(context, loaderManager,
                mView, repository);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testOnItemClick() throws Exception {
        Repository repository = new Repository();
        mPresenter.onItemClick(repository);
        verify(mView).showCommits(repository);
    }

    @Test
    public void testSuccessResponse() throws Exception {
        ApiFactory.setProvider(new TestProviderImpl(new SuccessGithubService(generateRepositories(12))));

        mPresenter.dispatchCreated();

        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView, times(2)).showRepositories(anyList());
    }

    @Test
    public void testErrorResponse() throws Exception {
        ApiFactory.setProvider(new TestProviderImpl(new FailGithubService()));

        mPresenter.dispatchCreated();

        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).showError();
    }

    @NonNull
    private List<Repository> generateRepositories(int count) {
        List<Repository> repositories = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            Repository repository = new Repository();
            repository.setName("Name" + i);
            repository.setLanguage("Language" + i);
            repository.setDescription("Description" + i);
            repository.setWatchersCount(i * 2 + 3);
            repository.setStarsCount(i * 2 + 5);
            repository.setForksCount(i % 5 + 2);
            repositories.add(repository);
        }
        return repositories;
    }

    private class SuccessGithubService extends TestGithubService {

        private final List<Repository> mRepositories;

        public SuccessGithubService(@NonNull List<Repository> repositories) {
            mRepositories = repositories;
        }

        @Override
        public Observable<List<Repository>> repositories() {
            return Observable.just(mRepositories);
        }
    }

    private class FailGithubService extends TestGithubService {

        @Override
        public Observable<List<Repository>> repositories() {
            return Observable.error(new IOException());
        }
    }

    private class TestRepositoryImpl implements RepositoriesRepository {

        private final List<Repository> mRepositories;

        public TestRepositoryImpl(@NonNull List<Repository> repositories) {
            mRepositories = repositories;
        }

        @NonNull
        @Override
        public Observable<List<Repository>> cachedRepositories() {
            return Observable.just(mRepositories);
        }

        @Override
        public void saveRepositories(@NonNull List<Repository> repositories) {
            // Do nothing
        }
    }

}
