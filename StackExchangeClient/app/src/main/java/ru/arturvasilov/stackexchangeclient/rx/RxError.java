package ru.arturvasilov.stackexchangeclient.rx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import ru.arturvasilov.stackexchangeclient.dialog.NetworkErrorDialog;
import ru.arturvasilov.stackexchangeclient.dialog.UnexpectedErrorDialog;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;

/**
 * @author Artur Vasilov
 */
public final class RxError {

    private RxError() {
    }

    @NonNull
    public static ErrorView view(@NonNull Context context, @NonNull FragmentManager fm) {
        return new ErrorViewImpl(context, fm);
    }

    private static class ErrorViewImpl implements ErrorView {

        private final Context mContext;
        private final FragmentManager mFm;
        private final Fragment mFragment;

        public ErrorViewImpl(@NonNull Context context, @NonNull FragmentManager fm) {
            mContext = context;
            mFm = fm;
            mFragment = null;
        }

        @Override
        public void showNetworkError() {
            new NetworkErrorDialog().show(
                    mFragment != null
                            ? mFragment.getFragmentManager()
                            : mFm,
                    NetworkErrorDialog.class.getName());
        }

        @Override
        public void showUnexpectedError() {
            new UnexpectedErrorDialog().show(
                    mFragment != null
                            ? mFragment.getFragmentManager()
                            : mFm,
                    UnexpectedErrorDialog.class.getName());
        }

        @Override
        public void showErrorMessage(@NonNull String message) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

}
