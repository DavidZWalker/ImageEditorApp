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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class LibraryFragment extends Fragment {

    private LibraryViewModel mViewModel;
    private RecyclerView imageRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView libraryTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ImageFileHandler imageFileHandler = new ImageFileHandler(getContext().getFilesDir(), ImageFileHandler.IMAGE_DIR_LIB);
        mViewModel = ViewModelProviders.of(this, new LibraryViewModelFactory(imageFileHandler)).get(LibraryViewModel.class);

        return inflater.inflate(R.layout.library_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageRecyclerView = getActivity().findViewById(R.id.imageRecyclerView);
        progressBar = getActivity().findViewById(R.id.libProgressBar);
        libraryTextView = getActivity().findViewById(R.id.libraryTextView);
        mAdapter = new ImageAdapter(new ArrayList<>());

        // init RecyclerView
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadLibraryImagesTask().execute();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new LoadLibraryImagesTask().execute();
        }
    }

    private class LoadLibraryImagesTask extends AsyncTask<Void, Bitmap, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            libraryTextView.setText(getResources().getString(R.string.library_loading_text));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (File f : mViewModel.getUnloadedImageFiles()) {
                Bitmap toAdd = mViewModel.getBitmapFromFile(f);
                publishProgress(toAdd);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
            Bitmap toAdd = values[0];
            mAdapter.addToBitmapList(toAdd);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
            libraryTextView.setText(getResources().getString(R.string.empty_list_text));
        }
    }
}