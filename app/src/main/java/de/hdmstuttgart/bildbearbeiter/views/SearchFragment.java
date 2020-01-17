package de.hdmstuttgart.bildbearbeiter.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.adapters.ImageAdapter;
import de.hdmstuttgart.bildbearbeiter.models.SearchResponseResult;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;
import de.hdmstuttgart.bildbearbeiter.viewmodels.SearchViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The type Search fragment offers the functionality to search an API for pictures with a query,
 * the
 */
public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private Button searchButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar searchProgressBar;
    private ImageAdapter searchAdapter;
    private EditText editText;
    private final String logTag = "SearchFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(logTag, "Inflating view for SearchFragment...");
        //inflate view to work with it
        final View view = inflater.inflate(R.layout.search_fragment, container, false);
        //setting onClick on SearchButton
        searchProgressBar = view.findViewById(R.id.progress_search);
        editText = view.findViewById(R.id.editTextSearchPictures);
        searchButton = view.findViewById(R.id.buttonSearchPictures);

        searchButton.setOnClickListener(v -> {
            Log.d(logTag, "User clicked search.");
            UIUtil.hideKeyboard(getActivity());
            searchAdapter.clearBitmapList();
            searchProgressBar.setVisibility(View.VISIBLE);
            doSearch();
        });

        //assign recycle
        recyclerView = view.findViewById(R.id.recyclerSearch);
        linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        //adapter
        searchAdapter = new ImageAdapter(new ArrayList<>());
        recyclerView.setAdapter(searchAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        Log.d(logTag, "View for SearchFragment created successfully!");
    }

    private void doSearch() {
        Log.d(logTag, "Doing search...");
        searchButton.setEnabled(false);
        searchButton.setText(R.string.searchingText);
        if (!checkInternetConnection()) {
            UIUtil.showShortSnackbar(getView(), "No Internet Connection, check your settings!");
            searchProgressBar.setVisibility(View.GONE);
            searchButton.setText(R.string.searchButtons);
            searchButton.setEnabled(true);
        }
        //get Search String from User
        String query = editText.getText().toString();

        Call<SearchResponseResult> call = mViewModel.doSearch(query);
        if (call != null) {
            editText.setText("");
            call.enqueue(new Callback<SearchResponseResult>() {
                @Override
                public void onResponse(Call<SearchResponseResult> call, Response<SearchResponseResult> response) {
                    //get urls
                    if (response.isSuccessful()) {
                        Log.d(logTag, "Response received!");
                        if (response.body().getPhotos().size() == 0) {
                            onNoResultsFound();
                            return;
                        }
                        List<SearchResponseResult.Photo> photoList = response.body().getPhotos();
                        new DownloadFilesTask().execute(photoList.toArray(new SearchResponseResult.Photo[photoList.size()]));
                    }
                }

                private void onNoResultsFound() {
                    Log.d(logTag, "No results found for user query...");
                    UIUtil.showShortSnackbar(getView(), "No results were found, try something else!");
                    searchProgressBar.setVisibility(View.GONE);
                    searchButton.setText(R.string.searchButtons);
                    searchButton.setEnabled(true);
                }

                @Override
                public void onFailure(Call<SearchResponseResult> call, Throwable t) {
                    Log.e(logTag, "Failed to search!", t);
                    searchProgressBar.setVisibility(View.GONE);
                    searchButton.setText(R.string.searchButtons);
                    searchButton.setEnabled(true);
                }
            });
        } else {
            UIUtil.showShortSnackbar(getView(), "Please enter something in the search field");
            searchProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Checks if there is a internet connection.
     *
     * @return the boolean
     */
    public boolean checkInternetConnection() {
        Log.d(logTag, "Checking internet connection...");
        return mViewModel.checkInternetConnection((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE));
    }

    /*
     * AsyncTask which downloads a Bitmap from a specific URL.
     */
    private class DownloadFilesTask extends AsyncTask<SearchResponseResult.Photo, Bitmap, Void> {

        protected Void doInBackground(SearchResponseResult.Photo... photos) {
            Log.d(logTag, "Doing search async...");
            // get Bitmap for each search result
            for (SearchResponseResult.Photo photo : photos) {
                Bitmap bmp = mViewModel.getBitmapFromSearchResponse(photo);
                if (bmp != null) publishProgress(bmp);
            }

            return null;
        }

        protected void onProgressUpdate(Bitmap... downloadedImage) {
            Log.d(logTag, "Progress updated! Publishing...");
            searchAdapter.addToBitmapList(downloadedImage[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(logTag, "Search complete! Finalizing...");
            searchProgressBar.setVisibility(View.GONE);
            searchButton.setText(R.string.searchButtons);
            searchButton.setEnabled(true);
        }
    }
}

