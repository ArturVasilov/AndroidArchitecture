package ru.arturvasilov.stackexchangeclient.presenter;

import android.content.Context;
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
import java.util.Locale;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Badge;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.model.content.UserTag;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.testutils.TestRemoteRepository;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import ru.arturvasilov.stackexchangeclient.view.ProfileView;
import rx.Observable;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class ProfilePresenterTest {

    private static final String ERROR_SHOWN_KEY = "error_shown";

    private ProfileView mView;
    private LoadingView mLoadingView;
    private ErrorView mErrorView;

    private ProfilePresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(ProfileView.class);
        Mockito.doNothing().when(mView).showUserName(anyString());
        Mockito.doNothing().when(mView).showUserImage(anyString());
        Mockito.doNothing().when(mView).showProfileLink(anyString());
        Mockito.doNothing().when(mView).showReputation(anyString());
        Mockito.doNothing().when(mView).showBadges(anyListOf(Badge.class));
        Mockito.doNothing().when(mView).showTopTags(anyListOf(UserTag.class));

        Context context = Mockito.mock(Context.class);
        Mockito.when(context.getString(eq(R.string.reputation), anyInt())).thenAnswer(invocation -> {
            int reputation = (int) invocation.getArguments()[1];
            return String.format(Locale.getDefault(), "Reputation: %1$d", reputation);
        });
        Mockito.when(context.getString(R.string.profile_text)).thenReturn("Profile");

        mLoadingView = MockUtils.mockLoadingView();
        mErrorView = MockUtils.mockErrorView();

        User user = new User();
        user.setUserId(932123);
        user.setAge(19);
        user.setName("Artur Vasilov");
        user.setReputation(2985);
        user.setLink("http://stackoverflow.com/users/3637200/vasilov-artur");
        user.setProfileImage("https://i.stack.imgur.com/EJNBv.jpg?sz=128&g=1");

        mPresenter = new ProfilePresenter(context, MockUtils.mockLoaderManager(), mView,
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
    public void testFullUserInfoShown() throws Exception {
        mPresenter.init(null);

        verifyUserInfoShown();
    }

    @Test
    public void testEmptyBadgesNotShown() throws Exception {
        mPresenter.init(null);

        Mockito.verify(mView, times(0)).showBadges(anyListOf(Badge.class));
    }

    @Test
    public void testEmptyTagsNotShown() throws Exception {
        mPresenter.init(null);

        Mockito.verify(mView, times(0)).showTopTags(anyListOf(UserTag.class));
    }

    @Test
    public void testBadgesAndTagsShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ContentRepository());

        mPresenter.init(null);

        Mockito.verify(mView).showBadges(anyListOf(Badge.class));
        Mockito.verify(mView).showTopTags(anyListOf(UserTag.class));
    }

    @Test
    public void testErrorShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ErrorRepository());

        mPresenter.init(null);
        Mockito.verify(mErrorView).showNetworkError();
    }

    @Test
    public void testErrorShownOnlyOnce() throws Exception {
        Bundle savedInstanceState = Mockito.mock(Bundle.class);
        Mockito.when(savedInstanceState.getBoolean(ERROR_SHOWN_KEY)).thenReturn(true);

        RepositoryProvider.setRemoteRepository(new ErrorRepository());
        mPresenter.init(savedInstanceState);

        Mockito.verifyNoMoreInteractions(mErrorView);
        verifyUserInfoShown();
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
    public void testErrorAvailableInformationShown() throws Exception {
        RepositoryProvider.setRemoteRepository(new ErrorRepository());

        mPresenter.init(null);
        verifyUserInfoShown();
    }

    @After
    public void tearDown() throws Exception {
        //noinspection ConstantConditions
        RepositoryProvider.setRemoteRepository(null);
    }

    private void verifyUserInfoShown() {
        Mockito.verify(mView).showUserImage("https://i.stack.imgur.com/EJNBv.jpg?sz=800&g=1");
        Mockito.verify(mView).showUserName("Artur Vasilov");
        Mockito.verify(mView).showReputation("Reputation: 2985");
        Mockito.verify(mView).showProfileLink("<a href=\"http://stackoverflow.com/users/3637200/vasilov-artur\">Profile</a>");
    }

    private class ContentRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Badge>> badges(int userId) {
            List<Badge> badges = new ArrayList<>();
            badges.add(new Badge());
            return Observable.just(badges);
        }

        @NonNull
        @Override
        public Observable<List<UserTag>> topTags(int userId) {
            List<UserTag> userTags = new ArrayList<>();
            userTags.add(new UserTag());
            return Observable.just(userTags);
        }
    }

    private class ErrorRepository extends TestRemoteRepository {

        @NonNull
        @Override
        public Observable<List<Badge>> badges(int userId) {
            return Observable.error(new UnknownHostException());
        }
    }
}
