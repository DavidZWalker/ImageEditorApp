package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.hdmstuttgart.bildbearbeiter.R;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private Button searchButton;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //inflate view to work with it
        final View view = inflater.inflate(R.layout.search_fragment,container,false);
        //setting onClick on SearchButton
        searchButton = view.findViewById(R.id.buttonSearchPictures);
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.progress_search).setVisibility(View.VISIBLE);
                searchPicturesOnline();
            }
        });


        return view;
    }

    private void searchPicturesOnline() {
        //TODO search unsplash
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        // TODO: Use the ViewModel
    }

    public void onClickSearch(View view){
        //Show progress bar
        view.findViewById(R.id.progress_search).setVisibility(View.VISIBLE);

    }

}
