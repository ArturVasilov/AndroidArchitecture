package ru.arturvasilov.stackexchangeclient.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import ru.arturvasilov.stackexchangeclient.BuildConfig;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.testutils.TestKeyValueStorage;
import ru.arturvasilov.stackexchangeclient.view.AuthView;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class AuthPresenterTest {

    private AuthView mAuthView;

    private TestKeyValueStorage mKeyValueStorage;

    @Before
    public void setUp() throws Exception {
        mAuthView = Mockito.mock(AuthView.class);
        Mockito.doNothing().when(mAuthView).showAuth(anyString());
        Mockito.doNothing().when(mAuthView).showWalkthrough();
        Mockito.doNothing().when(mAuthView).showMainScreen();
        Mockito.doNothing().when(mAuthView).close();

        mKeyValueStorage = new TestKeyValueStorage();
        RepositoryProvider.setKeyValueStorage(mKeyValueStorage);
    }

    @Test
    public void testCreated() throws Exception {
        AuthPresenter presenter = new AuthPresenter(mAuthView);
        assertNotNull(presenter);
    }

    @Test
    public void testEmptyInit() throws Exception {
        AuthPresenter presenter = new AuthPresenter(mAuthView);
        presenter.init();
        Mockito.verifyNoMoreInteractions(mAuthView);
    }

    @Test
    public void testOpenLogin() throws Exception {
        AuthPresenter presenter = new AuthPresenter(mAuthView);
        presenter.init();
        presenter.onLoginButtonClick();

        String url = "https://stackexchange.com/oauth/dialog?client_id=" + BuildConfig.CLIENT_ID +
                "&scope=read_inbox,no_expiry,write_access,private_info" +
                "&redirect_uri=https://stackexchange.com/oauth/login_success";
        Mockito.verify(mAuthView).showAuth(url);
    }

    @Test
    public void testOpenWalkthrough() throws Exception {
        AuthPresenter presenter = new AuthPresenter(mAuthView);
        mKeyValueStorage.saveAccessToken("token");

        presenter.init();
        Mockito.verify(mAuthView).showWalkthrough();
        Mockito.verify(mAuthView).close();
    }

    @Test
    public void testOpenMainScreen() throws Exception {
        AuthPresenter presenter = new AuthPresenter(mAuthView);
        mKeyValueStorage.saveAccessToken("token");
        mKeyValueStorage.saveWalkthroughPassed();

        presenter.init();
        Mockito.verify(mAuthView).showMainScreen();
        Mockito.verify(mAuthView).close();
    }

    @Test
    public void testHandleSuccessUrl() throws Exception {
        AuthPresenter presenter = new AuthPresenter(mAuthView);
        String url = "https://...#access_token=token";
        presenter.onSuccessUrl(url);

        assertEquals("token", RepositoryProvider.provideKeyValueStorage().obtainAccessToken());
    }

    @After
    public void tearDown() throws Exception {
        //noinspection ConstantConditions
        RepositoryProvider.setKeyValueStorage(null);
    }
}
