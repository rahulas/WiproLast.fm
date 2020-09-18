package com.rahul.wiprolastfmtask.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.rahul.wiprolastfmtask.R;
import com.rahul.wiprolastfmtask.ui.fragments.AlbumsFragment;
import com.rahul.wiprolastfmtask.ui.fragments.ArtistsFragment;
import com.rahul.wiprolastfmtask.ui.fragments.TracksFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    boolean doubleBackToExitPressedOnce = false;

    public static final int MENU_ALBUMS = 1;
    public static final int MENU_ARTISTS = 2;
    public static final int MENU_SONGS = 3;
    private FragmentTransaction mFragmentTransaction;

    private AlbumsFragment albumsFragment;
    private ArtistsFragment artistsFragment;
    private TracksFragment tracksFragment;

    private MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        bottomNavigation.add(new MeowBottomNavigation.Model(MENU_ALBUMS, R.drawable.ic_menu_album));
        bottomNavigation.add(new MeowBottomNavigation.Model(MENU_ARTISTS, R.drawable.ic_menu_artists));
        bottomNavigation.add(new MeowBottomNavigation.Model(MENU_SONGS, R.drawable.ic_menu_songs));

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                collapseSearchView();
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case MENU_ALBUMS: {
                        albumsFragment = new AlbumsFragment();
                        loadFragment(albumsFragment, AlbumsFragment.TAG);
                        break;
                    }
                    case MENU_ARTISTS: {
                        artistsFragment = new ArtistsFragment();
                        loadFragment(artistsFragment, ArtistsFragment.TAG);
                        break;
                    }
                    case MENU_SONGS: {
                        tracksFragment = new TracksFragment();
                        loadFragment(tracksFragment, TracksFragment.TAG);
                        break;
                    }

                }
            }
        });
        bottomNavigation.show(MENU_ALBUMS, false);

    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    private void loadFragment(Fragment fragment, String fragmentTAG) {
        invalidateOptionsMenu();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.homeContainer, fragment, fragmentTAG);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    private MenuItem searchMenuItem;
    private SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        searchMenuItem = menu.findItem(R.id.app_bar_search);

        searchView = (SearchView) searchMenuItem.getActionView();

        return super.onCreateOptionsMenu(menu);
    }

    private void collapseSearchView() {
        if (searchView != null)
            searchView.onActionViewCollapsed();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}