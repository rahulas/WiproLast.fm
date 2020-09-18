package com.rahul.wiprolastfmtask.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.rahul.wiprolastfmtask.R;

public class BaseActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private AppCompatTextView toolbarTitle;
    private AppCompatImageView navBackIv, toolbarSearchIv;
    private Menu searchMenu;
    private MenuItem itemSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
