package ru.arturvasilov.stackexchangeclient.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.testutils.TestKeyValueStorage;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;
import ru.arturvasilov.stackexchangeclient.view.WalkthroughView;
import rx.Observable;
import rx.schedulers.Schedulers;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class WalkthroughPresenterTest {

    static {
        MockUtils.setupTestSchedulers();
    }

    private WalkthroughView mView;

    private WalkthroughPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(WalkthroughView.class);
        Mockito.doNothing().when(mView).showBenefit(anyInt());
        Mockito.doNothing().when(mView).setActionButtonText(anyInt());
        Mockito.doNothing().when(mView).showLoadingSplash();
        Mockito.doNothing().when(mView).showError();
        Mockito.doNothing().when(mView).finishWalkthrough();

        Context context = MockUtils.mockContext();
        LoaderManager lm = MockUtils.mockLoaderManager();

        mPresenter = new WalkthroughPresenter(context, lm, mView);

        RepositoryProvider.setRemoteRepository(new TestRemoteRepository());
        RepositoryProvider.setKeyValueStorage(new TestKeyValueStorage());
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testInit() throws Exception {
        mPresenter.init(null);
        Mockito.verify(mView).showBenefit(0);
        Mockito.verify(mView).setActionButtonText(R.string.next_uppercase);
    }

    @Test
    public void testActionButton() throws Exception {
        mPresenter.init(null);

        mPresenter.onActionButtonClick();
        Mockito.verify(mView).showBenefit(1);
        Mockito.verify(mView, times(2)).setActionButtonText(R.string.next_uppercase);

        mPresenter.onActionButtonClick();
        Mockito.verify(mView).showBenefit(2);
        Mockito.verify(mView).setActionButtonText(R.string.finish_uppercase);

        mPresenter.onBenefitSelected(true, 1);
        Mockito.verify(mView, times(2)).showBenefit(1);
        Mockito.verify(mView, times(3)).setActionButtonText(R.string.next_uppercase);
    }

    @Test
    public void testErrorLoading() throws Exception {
        mPresenter.init(null);
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();

        Mockito.verify(mView).showError();
    }

    @Test
    public void testLoadingSplash() throws Exception {
        RepositoryProvider.setRemoteRepository(new LoadingRepository());

        mPresenter.init(null);
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();

        Mockito.verify(mView).showLoadingSplash();
    }

    @Test
    public void testFinishWalkthrough() throws Exception {
        RepositoryProvider.setRemoteRepository(new SuccessRepository());

        mPresenter.init(null);
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();
        mPresenter.onActionButtonClick();

        Mockito.verify(mView).finishWalkthrough();
    }

    @Test
    public void testStateSaved() throws Exception {
        RepositoryProvider.setRemoteRepository(new SuccessRepository());

        Bundle outState = Mockito.mock(Bundle.class);
        Mockito.doNothing().when(outState).putInt(anyString(), anyInt());
        Mockito.doNothing().when(outState).putBoolean(anyString(), anyBoolean());

        mPresenter.init(null);
        mPresenter.onBenefitSelected(true, 1);

        mPresenter.onSaveInstanceState(outState);

        Mockito.verify(outState).putInt("current_item", 1);
        Mockito.verify(outState).putBoolean("walkthrough_passed", false);
        Mockito.verify(outState).putBoolean("information_loaded", true);
        Mockito.verify(outState).putBoolean("error", false);
    }

    @Test
    public void testWalkthroughRestored() throws Exception {
        Bundle savedInstanceState = Mockito.mock(Bundle.class);
        Mockito.when(savedInstanceState.getInt("current_item")).thenReturn(2);
        Mockito.when(savedInstanceState.getBoolean(anyString())).thenReturn(false);

        mPresenter.init(savedInstanceState);

        Mockito.verify(mView).showBenefit(2);
        Mockito.verify(mView).setActionButtonText(R.string.finish_uppercase);
    }

    @Test
    public void testErrorRestored() throws Exception {
        Bundle savedInstanceState = Mockito.mock(Bundle.class);
        Mockito.when(savedInstanceState.getInt("current_item")).thenReturn(2);
        Mockito.when(savedInstanceState.getBoolean("walkthrough_passed")).thenReturn(true);
        Mockito.when(savedInstanceState.getBoolean("information_loaded")).thenReturn(false);
        Mockito.when(savedInstanceState.getBoolean("error")).thenReturn(true);

        mPresenter.init(savedInstanceState);

        Mockito.verify(mView, times(2)).showError();
    }

    @Test
    public void testRetry() throws Exception {
        mPresenter.init(null);
        mPresenter.onBenefitSelected(true, 2);
        mPresenter.onActionButtonClick();
        Mockito.verify(mView).showError();

        mPresenter.onRetryButtonClick();
        Mockito.verify(mView, times(2)).showError();

        RepositoryProvider.setRemoteRepository(new SuccessRepository());
        mPresenter.onRetryButtonClick();
        Mockito.verify(mView).finishWalkthrough();
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setKeyValueStorage(null);
        RepositoryProvider.setRemoteRepository(null);
    }

    private class SuccessRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Question>> questions(@NonNull String tag) {
            List<Question> questions = new ArrayList<>();
            questions.add(new Question());
            return Observable.just(questions);
        }
    }

    private class LoadingRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Question>> questions(@NonNull String tag) {
            List<Question> questions = new ArrayList<>();
            questions.add(new Question());
            return Observable.just(questions).delay(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.newThread());
        }
    }
}
