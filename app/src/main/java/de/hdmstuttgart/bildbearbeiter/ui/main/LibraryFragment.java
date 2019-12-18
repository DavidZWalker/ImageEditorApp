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

public class LibraryFragment extends Fragment {

    private LibraryViewModel mViewModel;
    private RecyclerView imageRecyclerView;
    private ImageAdapter mAdapter;

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.library_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageFileHandler imageFileHandler = new ImageFileHandler(getContext(), "BBImages");
        mViewModel = ViewModelProviders.of(this, new LibraryViewModelFactory(imageFileHandler)).get(LibraryViewModel.class);

        // get saved images into a list
        List<Bitmap> bitmaps = new ArrayList<>();
        try {
            bitmaps =  mViewModel.getSavedImages();
        } catch (FileNotFoundException e) {
            Snackbar.make(getView(), "Failed to load images.", Snackbar.LENGTH_LONG).show();
        }

        // init adapter for RecyclerView
        mAdapter = new ImageAdapter(bitmaps);

        // init RecyclerView
        imageRecyclerView = getActivity().findViewById(R.id.imageRecyclerView);
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        imageRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            List<Bitmap> bmps = mViewModel.getSavedImages();
            mAdapter.setBitmapList(bmps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
