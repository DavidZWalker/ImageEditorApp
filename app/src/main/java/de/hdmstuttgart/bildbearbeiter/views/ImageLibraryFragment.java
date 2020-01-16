package de.hdmstuttgart.bildbearbeiter.views;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.adapters.ImageAdapter;
import de.hdmstuttgart.bildbearbeiter.viewmodels.ImageLibraryViewModel;
import de.hdmstuttgart.bildbearbeiter.viewmodels.ImageLibraryViewModelFactory;

/**
 * The type Image library fragment.
 */
public class ImageLibraryFragment extends Fragment {

    private ImageLibraryViewModel mViewModel;
    private RecyclerView imageRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView libraryTextView;
    private final String logTag = "ImageLibraryFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(logTag, "Inflating view for ImageLibraryFragment...");
        mViewModel = ViewModelProviders.of(this, new ImageLibraryViewModelFactory(getContext().getFilesDir())).get(ImageLibraryViewModel.class);

        return inflater.inflate(R.layout.library_fragment, container, false);
    }
    /*
     * Creates the viewModel and assigns the Buttons and their onclick listeners.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageRecyclerView = getActivity().findViewById(R.id.imageRecyclerView);
        progressBar = getActivity().findViewById(R.id.libProgressBar);
        libraryTextView = getActivity().findViewById(R.id.libraryTextView);
        mAdapter = new ImageAdapter(new ArrayList<>(), mViewModel.getModel());

        // init RecyclerView
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageRecyclerView.setAdapter(mAdapter);
        Log.d(logTag, "View created successfully");
    }

    @Override
    public void onResume() {
        Log.d(logTag, "Fragment resumed. Loading images...");
        super.onResume();
        new LoadLibraryImagesTask().execute();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d(logTag, "Fragment became visible. Loading images...");
            new LoadLibraryImagesTask().execute();
        }
    }
    /*
     * An AsyncTask which loads images from the storage and displays a placeholder text if no images were loaded.
     */
    private class LoadLibraryImagesTask extends AsyncTask<Void, Bitmap, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(logTag, "Initializing loading images async...");
            progressBar.setVisibility(View.VISIBLE);
            libraryTextView.setText(getResources().getString(R.string.library_loading_text));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(logTag, "Loading images...");
            for (File f : mViewModel.getUnloadedImageFiles()) {
                Log.d(logTag, "Loading image from file: " + f.getPath());
                Bitmap toAdd = mViewModel.getBitmapFromFile(f);
                publishProgress(toAdd);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
            Log.d(logTag, "Progress updated! Publishing...");
            Bitmap toAdd = values[0];
            mAdapter.addToBitmapList(toAdd);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(logTag, "Image loading completed.");
            progressBar.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
            libraryTextView.setText(getResources().getString(R.string.empty_list_text));
        }
    }
}