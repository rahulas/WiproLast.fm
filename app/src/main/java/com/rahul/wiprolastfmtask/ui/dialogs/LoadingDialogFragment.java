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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.rahul.wiprolastfmtask.R;

public class LoadingDialogFragment extends DialogFragment {
    public static final String FRAGMENT_TAG = "LoadingFragment";
    public static final String LOADING_MSG = "progress_message";
    private ImageView ivLoading;
    private TextView loadingMsgTv;
    private String progressMsg = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.custom_progress_layout, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getString(LOADING_MSG) != null)
                progressMsg = bundle.getString(LOADING_MSG);
        }
        if (rootView != null) {
            ivLoading = (ImageView) rootView.findViewById(R.id.spinnerImageView);
            loadingMsgTv = (TextView) rootView.findViewById(R.id.message);

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
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //dialog.setContentView(R.layout.custom_progress_layout);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            //lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            dialog.show();

            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.spinner);
            // animation.setFillAfter(true);
            ivLoading.startAnimation(animation);
            loadingMsgTv.setText(progressMsg);
        }
    }
}
