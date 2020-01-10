package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class LibraryViewModel extends ViewModel {

    private ImageFileHandler imageFileHandler;
    private List<File> loadedFiles = new ArrayList<>();

    public LibraryViewModel(ImageFileHandler imageFileHandler)
    {
        super();
        this.imageFileHandler = imageFileHandler;
    }

    public List<File> getUnloadedImageFiles() {
        List<File> allFiles = new ArrayList<>(Arrays.asList(imageFileHandler.getImageFolder().listFiles()));
        return allFiles.stream()
                .filter(x -> !loadedFiles.contains(x))
                .collect(Collectors.toList());
    }

    public Bitmap getBitmapFromFile(File file) {
        loadedFiles.add(file);
        return imageFileHandler.getImage(file.getName());
    }
}
