package ru.itis.lectures.githubmvp.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import retrofit2.http.Body;
import retrofit2.http.Header;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.content.auth.Authorization;
import ru.itis.lectures.githubmvp.rx.RxUtils;
import ru.itis.lectures.githubmvp.testutils.TestGithubService;
import ru.itis.lectures.githubmvp.testutils.TestProviderImpl;
import ru.itis.lectures.githubmvp.testutils.prefs.SharedPreferencesMapImpl;
import ru.itis.lectures.githubmvp.view.LogInView;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Artur Vasilov
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Base64.class})
public class LogInPresenterTest {

    private static final String TEST_TOKEN = "test_token";

    private SharedPreferences mPreferences;
    private Context mContext;

    private LogInView mView;
    private LogInPresenter mPresenter;

    static {
        RxUtils.setupTestSchedulers();
    }

    @Before
    public void setUp() throws Exception {
        mockStatic(Base64.class);
        PowerMockito.when(Base64.encodeToString(any(byte[].class), any(Integer.class))).thenAnswer(invocation -> {
            byte[] bytes = (byte[]) invocation.getArguments()[0];
            return new String(bytes);
        });

        mPreferences = new SharedPreferencesMapImpl();
        mContext = RxUtils.rxContext();
        when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mPreferences);

        LoaderManager lm = RxUtils.rxLoaderManager();

        mView = mock(LogInView.class);
        doNothing().when(mView).openRepositoriesScreen();
        doNothing().when(mView).showLoginError();
        doNothing().when(mView).showPasswordError();
        mPresenter = new LogInPresenter(mContext, lm, mView);
    }

    @Test
    public void testCheckAuthorizedTrue() throws Exception {
        Settings.putToken(mContext, TEST_TOKEN);

        mPresenter.checkIfAuthorized();
        verify(mView).openRepositoriesScreen();
        mPreferences.edit().clear().apply();
    }

    @Test
    public void testCheckAuthorizedFalse() throws Exception {
        mPreferences.edit().clear().apply();

        mPresenter.checkIfAuthorized();
        verify(mView, times(0)).openRepositoriesScreen();
    }

    @Test
    public void testEmptyLogin() throws Exception {
        mPreferences.edit().clear().apply();

        mPresenter.tryLogIn("", "password");
        verify(mView).showLoginError();
    }

    @Test
    public void testEmptyPassword() throws Exception {
        mPreferences.edit().clear().apply();

        mPresenter.tryLogIn("login", "");
        verify(mView).showPasswordError();
    }

    @Test
    public void testSuccessAuth() throws Exception {
        mPreferences.edit().clear().apply();
        ApiFactory.setProvider(new TestProviderImpl(new GithubServiceSuccess()));

        mPresenter.tryLogIn("login", "password");
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).openRepositoriesScreen();

        assertEquals(TEST_TOKEN, Settings.getToken(mContext));
    }

    @Test
    public void testErrorAuth() throws Exception {
        mPreferences.edit().clear().apply();
        ApiFactory.setProvider(new TestProviderImpl(new GithubServiceFail()));

        mPresenter.tryLogIn("login", "password");
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).showError();

        assertTrue(mPreferences.getAll().isEmpty());
    }

    @Test
    public void testSuccessAuthLoginSaved() throws Exception {
        mPreferences.edit().clear().apply();
        ApiFactory.setProvider(new TestProviderImpl(new GithubServiceSuccess()));

        final String login = "login";
        mPresenter.tryLogIn(login, "password");
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).openRepositoriesScreen();

        assertEquals(login, Settings.getUserName(mContext));
    }

    private class GithubServiceSuccess extends TestGithubService {

        @Override
        public Observable<Authorization> authorize(@Header("Authorization") String authorization, @Body JsonObject params) {
            Authorization authorization1 = new Authorization();
            authorization1.setId(555);
            authorization1.setToken(TEST_TOKEN);
            return Observable.just(authorization1);
        }
    }

    private class GithubServiceFail extends TestGithubService {

        @Override
        public Observable<Authorization> authorize(@Header("Authorization") String authorization, @Body JsonObject params) {
            return Observable.error(new IOException());
        }
    }
}
