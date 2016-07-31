package ru.arturvasilov.stackexchangeclient.rx.rxloader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RxResultTest {

    @Test
    public void testOnNext() throws Exception {
        RxResult<Integer> result = RxResult.onNext(5);
        assertEquals(Integer.valueOf(5), result.getResult());
        assertNull(result.getError());
        assertFalse(result.isOnComplete());
    }

    @Test
    public void testOnError() throws Exception {
        Throwable error = new Exception();
        RxResult<Integer> result = RxResult.onError(error);
        assertNull(result.getResult());
        assertTrue(error == result.getError());
        assertFalse(result.isOnComplete());
    }

    @Test
    public void testOnComplete() throws Exception {
        RxResult<Integer> result = RxResult.onComplete();
        assertNull(result.getResult());
        assertNull(result.getError());
        assertTrue(result.isOnComplete());
    }
}
