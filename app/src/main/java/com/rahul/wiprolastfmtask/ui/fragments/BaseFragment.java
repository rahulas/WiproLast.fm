package com.rahul.wiprolastfmtask.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.rahul.wiprolastfmtask.ui.dialogs.ErrorDialogFrag;
import com.rahul.wiprolastfmtask.ui.dialogs.LoadingDialogFragment;

public class BaseFragment extends Fragment {

    public void showLoadingDialog(String loadingMsg) {
        LoadingDialogFragment fragment = (LoadingDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(LoadingDialogFragment.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new LoadingDialogFragment();
            fragment.setCancelable(false);
            Bundle bundle = new Bundle();
            bundle.putString(LoadingDialogFragment.LOADING_MSG, loadingMsg);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(fragment, LoadingDialogFragment.FRAGMENT_TAG)
                    .commitAllowingStateLoss();

        }
    }

    public void hideLoadingDialog() {
        LoadingDialogFragment fragment = (LoadingDialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(LoadingDialogFragment.FRAGMENT_TAG);
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    public void showErrorDialog() {
        ErrorDialogFrag fragment = (ErrorDialogFrag) getActivity().getSupportFragmentManager().findFragmentByTag(ErrorDialogFrag.TAG);
        if (fragment == null) {
            fragment = new ErrorDialogFrag();
            fragment.setCancelable(false);
            /*Bundle bundle = new Bundle();
            bundle.putString(ErrorDialogFrag.LOADING_MSG, loadingMsg);
            fragment.setArguments(bundle);*/
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(fragment, ErrorDialogFrag.TAG)
                    .commitAllowingStateLoss();

        }
    }

    public void hideErrorDialog() {
        ErrorDialogFrag fragment = (ErrorDialogFrag) getActivity().getSupportFragmentManager().findFragmentByTag(ErrorDialogFrag.TAG);
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    private void showSnackBar(Context activity) {

    }


}
