package ru.itis.lectures.githubmvp.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.MaterialDialog;

import ru.itis.lectures.githubmvp.R;

/**
 * @author Artur Vasilov
 */
public class LoadingDialog extends DialogFragment {

    private static final String TAG = LoadingDialog.class.getSimpleName();
    private static final String TEXT_KEY = "text_id";

    @NonNull
    public static LoadingDialog create(@StringRes int textId) {
        Bundle bundle = new Bundle();
        bundle.putInt(TEXT_KEY, textId);
        LoadingDialog dialog = new LoadingDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void show(@NonNull FragmentManager manager) {
        show(manager, TAG);
    }

    public void cancel() {
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String text = getString(getArguments().getInt(TEXT_KEY, R.string.loading_progress));
        return new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .title(R.string.please_wait)
                .content(text)
                .build();
    }
}
