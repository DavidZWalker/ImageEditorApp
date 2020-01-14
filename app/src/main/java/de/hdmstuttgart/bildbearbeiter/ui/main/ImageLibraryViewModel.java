package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import androidx.lifecycle.ViewModel;
import java.io.File;
import java.io.IOException;
import java.util.List;
import de.hdmstuttgart.bildbearbeiter.ImageLibrary;

public class ImageLibraryViewModel extends ViewModel {

    private ImageLibrary model;

    public ImageLibraryViewModel(File appFilesDir)
    {
        super();
        model = new ImageLibrary(appFilesDir);
    }

    public List<File> getUnloadedImageFiles() {
        return model.getUnloadedImageFiles();
    }

    public Bitmap getBitmapFromFile(File file) {
        try {
            return model.getBitmapFromFile(file);
        } catch (IOException e) {
            return null;
        }
    }
}
