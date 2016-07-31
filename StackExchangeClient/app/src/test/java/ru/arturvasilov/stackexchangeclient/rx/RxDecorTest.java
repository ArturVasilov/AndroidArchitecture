package ru.arturvasilov.stackexchangeclient.rx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.net.SocketTimeoutException;

import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RxDecorTest {

    @Test
    public void testLoadingView() throws Exception {
        LoadingView loadingView = MockUtils.mockLoadingView();
        Observable.just(5)
                .compose(RxDecor.loading(loadingView))
                .subscribe(new StubAction<>());

        Mockito.verify(loadingView).showLoadingIndicator();
        Mockito.verify(loadingView).hideLoadingIndicator();
    }

    @Test
    public void testNetworkError() throws Exception {
        ErrorView errorView = MockUtils.mockErrorView();
        Observable.error(new SocketTimeoutException())
                .subscribe(new StubAction<>(), RxDecor.error(errorView));

        Mockito.verify(errorView).showNetworkError();
    }

    @Test
    public void testUnexpectedError() throws Exception {
        ErrorView errorView = MockUtils.mockErrorView();
        Observable.error(new Exception())
                .subscribe(new StubAction<>(), RxDecor.error(errorView));

        Mockito.verify(errorView).showUnexpectedError();
    }
}
