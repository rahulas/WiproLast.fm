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
import com.rahul.wiprolastfmtask.models.Track;
import com.rahul.wiprolastfmtask.ui.activities.DetailsActivity;
import com.rahul.wiprolastfmtask.ui.activities.MainActivity;
import com.rahul.wiprolastfmtask.ui.adapters.TrackAdapter;
import com.rahul.wiprolastfmtask.utils.DimenUtils;
import com.rahul.wiprolastfmtask.utils.RecyclerviewClickListeners;
import com.rahul.wiprolastfmtask.viewmodels.LFmViewModel;

import java.util.ArrayList;
import java.util.List;

public class TracksFragment extends BaseFragment {

    public static final String TAG = TracksFragment.class.getSimpleName();

    private LFmViewModel viewModel;

    private RecyclerView tracksRv;
    private AppCompatTextView textView;

    private List<Track> trackList;
    private TrackAdapter trackAdapter;


    public TracksFragment() {
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
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LFmViewModel.class);
        initViews(view);

        trackList = new ArrayList<>();
        trackAdapter = new TrackAdapter(requireActivity(), trackList);
        tracksRv.setAdapter(trackAdapter);
        viewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> artists) {
                if (artists != null) {
                    trackList.clear();
                    trackList = artists;
                    updateTracksRv(trackList);
                    trackAdapter.setTrackAdapterListener(new TrackAdapter.TrackAdapterListener() {
                        @Override
                        public void onTrackClicked(int position, Track track) {
                            moveToDetailsScreen(track);
                        }
                    });
                } else {
                    showErrorDialog();
                }
                hideLoadingDialog();
                collapseSearchView();

                if (trackAdapter.getItemCount() > 0)
                    textView.setVisibility(View.GONE);
                else
                    textView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void moveToDetailsScreen(Track track) {
        Intent in = new Intent(requireActivity(), DetailsActivity.class);
        in.putExtra(DetailsActivity.SELECTED_TYPE, MainActivity.MENU_SONGS);
        in.putExtra(DetailsActivity.SELECTED_TYPE_POJO, new Gson().toJson(track));
        requireActivity().startActivity(in);
    }

    private void updateTracksRv(List<Track> trackList) {
        if (trackList.size() > 0) {
            trackAdapter = new TrackAdapter(requireActivity(), trackList);
            tracksRv.setAdapter(trackAdapter);
            trackAdapter.notifyDataSetChanged();
        } else {
            showErrorDialog();
        }
    }

    private void initViews(View view) {
        textView = view.findViewById(R.id.label1);
        tracksRv = view.findViewById(R.id.tracks_rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        tracksRv.setLayoutManager(mLayoutManager);
        tracksRv.setHasFixedSize(true);
        tracksRv.setNestedScrollingEnabled(false);
        tracksRv.setItemAnimator(new DefaultItemAnimator());
        tracksRv.addItemDecoration(new RecyclerviewClickListeners.VerticalSpaceItemDecoration(DimenUtils.convertDpToPx(10)));
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
                showLoadingDialog(getString(R.string.searching_tracks));
                viewModel.searchTracksOnQuery(query);
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