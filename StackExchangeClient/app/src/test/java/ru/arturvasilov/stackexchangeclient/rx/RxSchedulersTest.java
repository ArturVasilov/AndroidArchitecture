package ru.arturvasilov.stackexchangeclient.rx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ru.arturvasilov.stackexchangeclient.testutils.MockUtils;
import rx.Observable;

import static org.junit.Assert.assertEquals;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RxSchedulersTest {

    static {
        MockUtils.setupTestSchedulers();
    }

    @Test
    public void test() throws Exception {
        Observable.just(3)
                .compose(RxSchedulers.async())
                .subscribe(integer -> assertEquals(3, integer.intValue()));
    }
}
