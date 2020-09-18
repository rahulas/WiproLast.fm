package com.rahul.wiprolastfmtask.ui.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.rahul.wiprolastfmtask.R;

public class ErrorDialogFrag extends DialogFragment {

    public static final String TAG = ErrorDialogFrag.class.getSimpleName();
    private AppCompatButton okButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.custom_error_dialog, container, false);


        if (rootView != null) {
            okButton = rootView.findViewById(R.id.b3);
        }
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            dialog.getWindow().setAttributes(lp);
            dialog.show();

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(ErrorDialogFrag.this).commitAllowingStateLoss();
                }
            });
        }
    }
    public void hideErrorDialog() {
        ErrorDialogFrag fragment = (ErrorDialogFrag) getActivity().getSupportFragmentManager().findFragmentByTag(ErrorDialogFrag.TAG);
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }
}
