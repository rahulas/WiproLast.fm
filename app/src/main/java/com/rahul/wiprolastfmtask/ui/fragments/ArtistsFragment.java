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
import com.rahul.wiprolastfmtask.models.Artist;
import com.rahul.wiprolastfmtask.ui.activities.DetailsActivity;
import com.rahul.wiprolastfmtask.ui.activities.MainActivity;
import com.rahul.wiprolastfmtask.ui.adapters.ArtistsAdapter;
import com.rahul.wiprolastfmtask.utils.DimenUtils;
import com.rahul.wiprolastfmtask.utils.RecyclerviewClickListeners;
import com.rahul.wiprolastfmtask.viewmodels.LFmViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArtistsFragment extends BaseFragment {

    public static final String TAG = ArtistsFragment.class.getSimpleName();

    private LFmViewModel viewModel;

    private RecyclerView artistRv;
    private AppCompatTextView textView;

    private List<Artist> artistList;
    private ArtistsAdapter artistsAdapter;


    public ArtistsFragment() {
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
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LFmViewModel.class);
        initViews(view);

        artistList = new ArrayList<>();
        artistsAdapter = new ArtistsAdapter(requireActivity(), artistList);
        artistRv.setAdapter(artistsAdapter);
        viewModel.getArtists().observe(getViewLifecycleOwner(), new Observer<List<Artist>>() {
            @Override
            public void onChanged(final List<Artist> artists) {
                if (artists != null) {
                    artistList.clear();
                    artistList = artists;
                    updateArtistsRv(artistList);
                    artistsAdapter.setArtistsAdapterListener(new ArtistsAdapter.ArtistsAdapterListener() {
                        @Override
                        public void onArtistClicked(int position, Artist artist) {
                            moveToDetailsScreen(artist);
                        }
                    });
                } else {
                    showErrorDialog();
                }
                hideLoadingDialog();
                collapseSearchView();

                if (artistsAdapter.getItemCount() > 0)
                    textView.setVisibility(View.GONE);
                else textView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void moveToDetailsScreen(Artist artist) {
        Intent in = new Intent(requireActivity(), DetailsActivity.class);
        in.putExtra(DetailsActivity.SELECTED_TYPE, MainActivity.MENU_ARTISTS);
        in.putExtra(DetailsActivity.SELECTED_TYPE_POJO, new Gson().toJson(artist));
        requireActivity().startActivity(in);
    }

    private void updateArtistsRv(List<Artist> artistList) {
        if(artistList.size()>0) {
            artistsAdapter = new ArtistsAdapter(requireActivity(), artistList);
            artistRv.setAdapter(artistsAdapter);
            artistsAdapter.notifyDataSetChanged();
        } else {
            showErrorDialog();
        }
    }

    private void initViews(View view) {
        textView = view.findViewById(R.id.label1);
        artistRv = view.findViewById(R.id.artist_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        artistRv.setLayoutManager(mLayoutManager);
        artistRv.setHasFixedSize(true);
        artistRv.setNestedScrollingEnabled(false);
        artistRv.setItemAnimator(new DefaultItemAnimator());
        artistRv.addItemDecoration(new RecyclerviewClickListeners.VerticalSpaceItemDecoration(DimenUtils.convertDpToPx(10)));
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

            // The query string is passed to the ViewModel
            // And the spinner is activated
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                showLoadingDialog(getString(R.string.searching_artists));
                viewModel.searchArtistsOnQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    private void collapseSearchView() {
        if (searchView != null)
            searchView.onActionViewCollapsed();
    }
}