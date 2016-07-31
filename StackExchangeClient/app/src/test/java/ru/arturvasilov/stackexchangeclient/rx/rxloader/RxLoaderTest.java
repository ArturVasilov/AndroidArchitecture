package ru.arturvasilov.stackexchangeclient.rx.rxloader;

import android.app.LoaderManager;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.rx.StubAction;
import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import rx.Observable;
import rx.functions.Action0;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RxLoaderTest {

    private RxLoader<Integer> mRxLoader;

    @Before
    public void setUp() throws Exception {
        Context context = MockUtils.mockContext();
        LoaderManager lm = MockUtils.mockLoaderManager();
        mRxLoader = RxLoader.create(context, lm, R.id.test_loader_id, Observable.just(1));
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mRxLoader);
    }

    @Test
    public void testInitEmpty() throws Exception {
        Context context = MockUtils.mockContext();
        LoaderManager lm = MockUtils.mockLoaderManager();

        Action0 onComplete = Mockito.mock(Action0.class);
        mRxLoader = RxLoader.create(context, lm, R.id.test_loader_id,
                Observable.just(1).doAfterTerminate(onComplete));
        mRxLoader.init();

        Mockito.verify(onComplete).call();
    }

    @Test
    public void testInit() throws Exception {
        mRxLoader.init(integer -> {
            assertEquals(1, integer.intValue());
        });
    }

    @Test
    public void testInitError() throws Exception {
        Context context = MockUtils.mockContext();
        LoaderManager lm = MockUtils.mockLoaderManager();
        Exception exception = new IOException();
        mRxLoader = RxLoader.create(context, lm, R.id.test_loader_id, Observable.error(exception));

        mRxLoader.init(new StubAction<>(), throwable -> assertTrue(throwable == exception));
    }

    @Test
    public void testInitOnComplete() throws Exception {
        Action0 onComplete = Mockito.mock(Action0.class);
        mRxLoader.init(new StubAction<>(), new StubAction<>(), onComplete);
        Mockito.verify(onComplete).call();
    }

    @Test
    public void testRestartEmpty() throws Exception {
        Context context = MockUtils.mockContext();
        LoaderManager lm = MockUtils.mockLoaderManager();

        Action0 onComplete = Mockito.mock(Action0.class);
        mRxLoader = RxLoader.create(context, lm, R.id.test_loader_id,
                Observable.just(1).doAfterTerminate(onComplete));
        mRxLoader.restart();

        Mockito.verify(onComplete).call();
    }

    @Test
    public void testRestart() throws Exception {
        mRxLoader.restart(integer -> {
            assertEquals(1, integer.intValue());
        });
    }

    @Test
    public void testRestartError() throws Exception {
        Context context = MockUtils.mockContext();
        LoaderManager lm = MockUtils.mockLoaderManager();
        Exception exception = new IOException();
        mRxLoader = RxLoader.create(context, lm, R.id.test_loader_id, Observable.error(exception));

        mRxLoader.restart(new StubAction<>(), throwable -> assertTrue(throwable == exception));
    }

    @Test
    public void testRestartOnComplete() throws Exception {
        Action0 onComplete = Mockito.mock(Action0.class);
        mRxLoader.restart(new StubAction<>(), new StubAction<>(), onComplete);
        Mockito.verify(onComplete).call();
    }
}
