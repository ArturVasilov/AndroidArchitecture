package ru.arturvasilov.stackexchangeclient.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public class StackDroidFCMInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            Analytics.setFcmRegistrationKey(token);
        }
    }

}
