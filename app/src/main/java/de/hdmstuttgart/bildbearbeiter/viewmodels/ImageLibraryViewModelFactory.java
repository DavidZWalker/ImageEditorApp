package de.hdmstuttgart.bildbearbeiter.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

/**
 * The type Image library view model factory.
 */
public class ImageLibraryViewModelFactory implements ViewModelProvider.Factory {
    private File appFilesDir;

    /**
     * Factory for ImageLibraries creates a {@link de.hdmstuttgart.bildbearbeiter.models.ImageLibrary}
     *
     * @param appFilesDir the app files dir
     */
    public ImageLibraryViewModelFactory(File appFilesDir) {
        this.appFilesDir = appFilesDir;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ImageLibraryViewModel(appFilesDir);
    }
}
