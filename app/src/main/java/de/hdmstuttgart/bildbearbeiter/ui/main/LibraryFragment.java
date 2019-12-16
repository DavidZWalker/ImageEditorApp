package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.ImageFileHandler;

public class LibraryFragment extends Fragment {

    private LibraryViewModel mViewModel;
    private RecyclerView imageRecyclerView;
    private List<Bitmap> bitmaps;

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
        try {
            bitmaps =  mViewModel.getSavedImages();
        } catch (FileNotFoundException e) {
            Snackbar.make(getView(), "Failed to load images.", Snackbar.LENGTH_LONG).show();
            bitmaps = new ArrayList<>();
        }
        imageRecyclerView = getActivity().findViewById(R.id.imageRecyclerView);
        imageRecyclerView.setAdapter(new SearchAdapter(bitmaps));
    }
}
