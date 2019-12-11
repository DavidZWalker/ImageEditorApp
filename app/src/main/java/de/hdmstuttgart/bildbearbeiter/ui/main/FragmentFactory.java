package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentFactory {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public Fragment getFragment(int index) {
        switch (index)
        {
            case Constants.CAMERA_PAGE:
                // get camera fragment
                return getLibraryFragment();
            case Constants.SEARCH_PAGE:
                // get search fragment
                return getLibraryFragment();
            case Constants.LIBRARY_PAGE:
            default:
                return getLibraryFragment();
        }
    }

    private LibraryFragment getLibraryFragment()
    {
        return new LibraryFragment();
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int index = 1;
        if (getArguments() != null)
            index = getArguments().getInt(ARG_SECTION_NUMBER);

        View root = null;
        if (index == 1) root = inflater.inflate(R.layout.fragment_main, container, false);
        else root = inflater.inflate(R.layout.activity_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }*/
}