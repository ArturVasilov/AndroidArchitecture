package ru.arturvasilov.stackexchangeclient.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.model.response.ApiError;
import ru.arturvasilov.stackexchangeclient.model.response.ServerError;
import ru.arturvasilov.stackexchangeclient.model.response.UserResponse;
import rx.Observable;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class ErrorsHandlerTest {

    @Test
    public void testNoError() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User());
        UserResponse response = new UserResponse();
        response.setUsers(users);

        UserResponse checkedResponse = Observable.just(response)
                .compose(ErrorsHandler.handleErrors())
                .toBlocking()
                .first();

        assertTrue(response == checkedResponse);
    }

    @Test
    public void testErrorHandled() throws Exception {
        ApiError error = new ApiError();
        error.setErrorCode(404);
        error.setErrorMessage("Test error");

        Observable.just(error)
                .compose(ErrorsHandler.handleErrors())
                .subscribe(apiError -> fail(), throwable -> {
                    assertTrue(throwable instanceof ServerError);
                    assertTrue(((ServerError) throwable).getError() == error);
                });
    }
}
