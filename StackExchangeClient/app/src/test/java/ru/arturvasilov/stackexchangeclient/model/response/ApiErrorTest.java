package ru.arturvasilov.stackexchangeclient.model.response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class ApiErrorTest {

    @Test
    public void testCreated() throws Exception {
        ApiError apiError = new ApiError();
        assertNotNull(apiError);
    }

    @Test
    public void testErrorCode() throws Exception {
        ApiError apiError = new ApiError();
        int errorCode = 200;
        apiError.setErrorCode(errorCode);
        assertEquals(errorCode, apiError.getErrorCode());
    }

    @Test
    public void testErrorMessage() throws Exception {
        ApiError apiError = new ApiError();
        String errorMessage = "error";
        apiError.setErrorMessage(errorMessage);
        assertEquals(errorMessage, apiError.getErrorMessage());
    }

    @Test
    public void testErrorName() throws Exception {
        ApiError apiError = new ApiError();
        String errorName = "no_method";
        apiError.setErrorName(errorName);
        assertEquals(errorName, apiError.getErrorName());
    }

    @Test
    public void testError() throws Exception {
        ApiError apiError = new ApiError();
        apiError.setErrorCode(404);
        assertTrue(apiError.isError());
        apiError.setErrorCode(500);
        assertTrue(apiError.isError());
    }

    @Test
    public void testNoError() throws Exception {
        ApiError apiError = new ApiError();
        apiError.setErrorCode(0);
        assertFalse(apiError.isError());
        apiError.setErrorCode(200);
        assertFalse(apiError.isError());
        apiError.setErrorCode(302);
        assertFalse(apiError.isError());
    }

    @Test
    public void testAll() throws Exception {
        ApiError apiError = new ApiError();
        int errorCode = 0;
        String errorMessage = "";
        String errorName = "";

        apiError.setErrorCode(errorCode);
        apiError.setErrorMessage(errorMessage);
        apiError.setErrorName(errorName);

        assertEquals(errorCode, apiError.getErrorCode());
        assertEquals(errorMessage, apiError.getErrorMessage());
        assertEquals(errorName, apiError.getErrorName());
        assertFalse(apiError.isError());
    }
}
