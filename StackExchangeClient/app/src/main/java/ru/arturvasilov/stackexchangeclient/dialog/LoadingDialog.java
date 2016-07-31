package ru.arturvasilov.stackexchangeclient.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;

/**
 * @author Artur Vasilov
 */
public class LoadingDialog extends DialogFragment {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    @NonNull
    public static LoadingView view(@NonNull FragmentManager fm) {
        return new LoadingViewImpl(fm);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog)
                .setView(R.layout.fr_loading)
                .create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
    }

    private static class HideTask implements Runnable {

        private final Reference<FragmentManager> mFmRef;

        private int mAttempts = 10;

        public HideTask(@NonNull FragmentManager fm) {
            mFmRef = new WeakReference<>(fm);
        }

        @Override
        public void run() {
            HANDLER.removeCallbacks(this);
            final FragmentManager fm = mFmRef.get();
            if (fm != null) {
                final LoadingDialog dialog = (LoadingDialog) fm.findFragmentByTag(LoadingDialog.class.getName());
                if (dialog != null) {
                    dialog.dismissAllowingStateLoss();
                } else if (--mAttempts >= 0) {
                    HANDLER.postDelayed(this, 300);
                }
            }
        }
    }

    private static class LoadingViewImpl implements LoadingView {

        private final AtomicBoolean mWaitForHide = new AtomicBoolean();

        private final FragmentManager mFm;

        public LoadingViewImpl(@NonNull FragmentManager fm) {
            mFm = fm;
        }

        @Override
        public void showLoadingIndicator() {
            if (mWaitForHide.compareAndSet(false, true)) {
                //noinspection ConstantConditions
                new LoadingDialog().show(mFm, LoadingDialog.class.getName());
            }
        }

        @Override
        public void hideLoadingIndicator() {
            if (mWaitForHide.compareAndSet(true, false)) {
                //noinspection ConstantConditions
                HANDLER.post(new HideTask(mFm));
            }
        }
    }
}
