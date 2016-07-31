package ru.arturvasilov.stackexchangeclient.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import ru.arturvasilov.stackexchangeclient.R;

/**
 * @author Artur Vasilov
 */
public class UnexpectedErrorDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog)
                .setMessage(R.string.error_unexpected)
                .setPositiveButton(R.string.button_ok, null)
                .create();
    }
}
