package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.ImageFileHandler;
import utilities.LibraryImageFileHandler;

public class LibraryFragment extends Fragment {

    private LibraryViewModel mViewModel;
    private RecyclerView imageRecyclerView;
    private ImageAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.library_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageFileHandler imageFileHandler = new LibraryImageFileHandler(getContext().getFilesDir());
        mViewModel = ViewModelProviders.of(this, new LibraryViewModelFactory(imageFileHandler)).get(LibraryViewModel.class);

        // init adapter for RecyclerView
        mAdapter = new ImageAdapter(mViewModel.getSavedImages());

        // init RecyclerView
        imageRecyclerView = getActivity().findViewById(R.id.imageRecyclerView);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mAdapter.setBitmapList(mViewModel.getSavedImages());
    }
}
