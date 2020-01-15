package de.hdmstuttgart.bildbearbeiter.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.models.ImageLibrary;

public class ImageLibraryViewModel extends ViewModel {

    private ImageLibrary model;

    public ImageLibraryViewModel(File appFilesDir) {
        super();
        model = new ImageLibrary(appFilesDir);
    }

    public List<File> getUnloadedImageFiles() {
        return model.getUnloadedImageFiles();
    }

    public Bitmap getBitmapFromFile(File file) {
        try {
            return model.loadImageFile(file);
        } catch (IOException e) {
            return null;
        }
    }

    public void removeImage(Bitmap bmpToRemove) {
        model.removeImage(bmpToRemove);
    }

    public ImageLibrary getModel() {
        return model;
    }
}
