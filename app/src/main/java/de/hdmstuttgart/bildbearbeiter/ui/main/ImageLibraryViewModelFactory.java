package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.io.File;

public class ImageLibraryViewModelFactory implements ViewModelProvider.Factory {
    private File appFilesDir;

    public ImageLibraryViewModelFactory(File appFilesDir) {
        this.appFilesDir = appFilesDir;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new ImageLibraryViewModel(appFilesDir);
    }
}
