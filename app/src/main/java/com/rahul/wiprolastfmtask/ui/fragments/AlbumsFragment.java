package com.rahul.wiprolastfmtask.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rahul.wiprolastfmtask.R;
import com.rahul.wiprolastfmtask.models.Album;
import com.rahul.wiprolastfmtask.ui.activities.DetailsActivity;
import com.rahul.wiprolastfmtask.ui.activities.MainActivity;
import com.rahul.wiprolastfmtask.ui.adapters.AlbumAdapter;
import com.rahul.wiprolastfmtask.utils.DimenUtils;
import com.rahul.wiprolastfmtask.utils.RecyclerviewClickListeners;
import com.rahul.wiprolastfmtask.viewmodels.LFmViewModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumsFragment extends BaseFragment {

    public static final String TAG = AlbumsFragment.class.getSimpleName();

    private LFmViewModel viewModel;

    private RecyclerView albumRv;
    private AppCompatTextView textView;

    private List<Album> albumList;
    private AlbumAdapter albumAdapter;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LFmViewModel.class);
        initViews(view);

        albumList = new ArrayList<>();
        albumAdapter = new AlbumAdapter(requireActivity(), albumList);
        albumRv.setAdapter(albumAdapter);
        viewModel.getAlbums().observe(getViewLifecycleOwner(), new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                if (albums != null) {
                    albumList.clear();
                    albumList = albums;
                    updateAlbumsRv(albumList);
                    albumAdapter.setAlbumAdapterListener(new AlbumAdapter.AlbumAdapterListener() {
                        @Override
                        public void onAlbumClicked(int position, Album album) {
                            moveToDetailsScreen(album);
                        }
                    });
                } else {
                    showErrorDialog();
                }

                hideLoadingDialog();
                collapseSearchView();
                if (albumAdapter.getItemCount() > 0)
                    textView.setVisibility(View.GONE);
                else textView.setVisibility(View.VISIBLE);

            }
        });
    }

    private void moveToDetailsScreen(Album album) {
        Intent in = new Intent(requireActivity(), DetailsActivity.class);
        in.putExtra(DetailsActivity.SELECTED_TYPE, MainActivity.MENU_ALBUMS);
        in.putExtra(DetailsActivity.SELECTED_TYPE_POJO, new Gson().toJson(album));
        requireActivity().startActivity(in);
    }

    private void updateAlbumsRv(List<Album> albumList) {
        if (albumList.size() > 0) {
            albumAdapter = new AlbumAdapter(requireActivity(), albumList);
            albumRv.setAdapter(albumAdapter);
            albumAdapter.notifyDataSetChanged();
        } else {
            showErrorDialog();
        }
    }

    private void initViews(View view) {
        textView = view.findViewById(R.id.label1);
        albumRv = view.findViewById(R.id.album_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        albumRv.setLayoutManager(mLayoutManager);
        albumRv.setHasFixedSize(true);
        albumRv.setNestedScrollingEnabled(false);
        albumRv.setItemAnimator(new DefaultItemAnimator());
        albumRv.addItemDecoration(new RecyclerviewClickListeners.VerticalSpaceItemDecoration(DimenUtils.convertDpToPx(10)));
    }

    private Menu menuThis;
    private MenuItem searchMenuItem;
    private SearchView searchView;

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        this.menuThis = menu;
        searchMenuItem = menuThis.findItem(R.id.app_bar_search);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                showLoadingDialog(getString(R.string.searching_albums));
                viewModel.searchAlbumsOnQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*albumList.clear();
                updateAlbumsRv(albumList);*/
                return false;
            }
        });
    }

    private void collapseSearchView() {
        if (searchView != null)
            searchView.onActionViewCollapsed();
    }
}