package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import de.hdmstuttgart.bildbearbeiter.SearchResponseResult;
import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private Button searchButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    ProgressBar searchProgressBar;
    private ImageAdapter searchAdapter;

    private EditText editText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflate view to work with it
        final View view = inflater.inflate(R.layout.search_fragment, container, false);
        //setting onClick on SearchButton
        searchProgressBar = view.findViewById(R.id.progress_search);
        editText = view.findViewById(R.id.editTextSearchPictures);
        searchButton = view.findViewById(R.id.buttonSearchPictures);

        searchButton.setOnClickListener(v -> {
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
    }

    private void doSearch() {
        //get Search String from User
        String query = editText.getText().toString();

        Call<SearchResponseResult> call = mViewModel.doSearch(query);
        if (call != null) {
            editText.setText("");
            call.enqueue(new Callback<SearchResponseResult>() {
                @Override
                public void onResponse(Call<SearchResponseResult> call, Response<SearchResponseResult> response) {
                    //get urls
                    if (response.isSuccessful())
                        new DownloadFilesTask().execute((SearchResponseResult.Photo[]) response.body().getPhotos().toArray());
                }

                @Override
                public void onFailure(Call<SearchResponseResult> call, Throwable t) {
                    searchProgressBar.setVisibility(View.GONE);
                }
            });
        }
        else {
            Snackbar.make(getView(), "Please enter something in the search field", Snackbar.LENGTH_SHORT).show();
            searchProgressBar.setVisibility(View.GONE);
        }
    }

    private class DownloadFilesTask extends AsyncTask<SearchResponseResult.Photo, Bitmap, Void> {

        protected Void doInBackground(SearchResponseResult.Photo... photos) {
            // get Bitmap for each search result
            for (SearchResponseResult.Photo photo : photos) {
                Bitmap bmp = mViewModel.getBitmapFromSearchResponse(photo);
                if (bmp != null) publishProgress(bmp);
            }

            return null;
        }

        protected void onProgressUpdate(Bitmap... downloadedImage) {
            searchAdapter.addToBitmapList(downloadedImage[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            searchProgressBar.setVisibility(View.GONE);
        }
    }
}

