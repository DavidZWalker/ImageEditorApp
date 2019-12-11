package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.lifecycle.ViewModel;

import de.hdmstuttgart.bildbearbeiter.model.Library;

public class LibraryViewModel extends ViewModel {

    private final String imagesDirName = "myimages";
    private Library model;

    public LibraryViewModel()
    {
        super();
        model = new Library();
    }

    public void loadLib()
    {

    }
}
