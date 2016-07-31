package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class ServerErrorTest {

    private ApiError mApiError;

    @Before
    public void setUp() throws Exception {
        mApiError = new ApiError();
        mApiError.setErrorCode(500);
        mApiError.setErrorMessage("Bad request");
        mApiError.setErrorName("");
    }

    @Test
    public void testCreated() throws Exception {
        //noinspection ThrowableInstanceNeverThrown
        ServerError error = new ServerError(mApiError);
        assertNotNull(error);
    }

    @Test
    public void testGetApiError() throws Exception {
        //noinspection ThrowableInstanceNeverThrown
        ServerError error = new ServerError(mApiError);
        assertTrue(mApiError == error.getError());
    }

    @Test
    public void testGetMessage() throws Exception {
        //noinspection ThrowableInstanceNeverThrown
        ServerError error = new ServerError(mApiError);
        assertEquals(mApiError.getErrorMessage(), error.getMessage());
    }

    @Test(expected = ServerError.class)
    public void testThrow() throws Exception {
        throw new ServerError(mApiError);
    }
}
