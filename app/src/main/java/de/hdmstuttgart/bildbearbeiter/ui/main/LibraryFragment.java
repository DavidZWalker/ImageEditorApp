package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.ImageFileHandler;

public class LibraryFragment extends Fragment {

    private LibraryViewModel mViewModel;
    private RecyclerView imageRecyclerView;
    private ImageAdapter mAdapter;
    private TextView emptyListTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.library_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageFileHandler imageFileHandler = new ImageFileHandler(getContext().getFilesDir(), ImageFileHandler.IMAGE_DIR_LIB);
        mViewModel = ViewModelProviders.of(this, new LibraryViewModelFactory(imageFileHandler)).get(LibraryViewModel.class);
        emptyListTextView = getActivity().findViewById(R.id.emptyListTextView);

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
        if (isVisibleToUser) {
            mAdapter.setBitmapList(mViewModel.getSavedImages());
            if (mAdapter.getItemCount() == 0) {
                emptyListTextView.setVisibility(View.VISIBLE);
                imageRecyclerView.setVisibility(View.GONE);
            }
            else {
                emptyListTextView.setVisibility(View.GONE);
                imageRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}