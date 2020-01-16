package de.hdmstuttgart.bildbearbeiter.viewmodels;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.models.ImageLibrary;

/**
 * The type Image library view model.
 */
public class ImageLibraryViewModel extends ViewModel {

    private ImageLibrary model;

    /**
     * Instantiates a new Image library view model.
     *
     * @param appFilesDir the app files dir
     */
    public ImageLibraryViewModel(File appFilesDir) {
        super();
        model = new ImageLibrary(appFilesDir);
    }

    /**
     * Gets unloaded image files.
     *
     * @return the unloaded image files
     */
    public List<File> getUnloadedImageFiles() {
        return model.getUnloadedImageFiles();
    }

    /**
     * Gets bitmap from file.
     *
     * @param file the file
     * @return the bitmap from file
     */
    public Bitmap getBitmapFromFile(File file) {
        try {
            return model.loadImageFile(file);
        } catch (IOException e) {
            Log.e("Error", "Failed to load bitmap from file", e);
            return null;
        }
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public ImageLibrary getModel() {
        return model;
    }
}
