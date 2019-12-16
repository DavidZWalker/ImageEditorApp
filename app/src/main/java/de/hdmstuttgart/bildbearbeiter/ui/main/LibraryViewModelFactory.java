package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import utilities.ImageFileHandler;

public class LibraryViewModelFactory implements ViewModelProvider.Factory {
    private ImageFileHandler imageFileHandler;

    public LibraryViewModelFactory(ImageFileHandler imageFileHandler) {
        this.imageFileHandler = imageFileHandler;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new LibraryViewModel(imageFileHandler);
    }
}
