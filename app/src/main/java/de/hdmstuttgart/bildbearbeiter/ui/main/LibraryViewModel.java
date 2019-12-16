package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import utilities.ImageFileHandler;

public class LibraryViewModel extends ViewModel {

    ImageFileHandler imageFileHandler;

    public LibraryViewModel(ImageFileHandler imageFileHandler)
    {
        super();
        this.imageFileHandler = imageFileHandler;
    }

    public List<Bitmap> getSavedImages() throws FileNotFoundException {
        List<Bitmap> bitmaps = new ArrayList<>();
        File[] files = imageFileHandler.getImageFolder().listFiles();
        for (File f : files)
            bitmaps.add(imageFileHandler.getImage(f.getName()));
        return bitmaps;
    }
}
