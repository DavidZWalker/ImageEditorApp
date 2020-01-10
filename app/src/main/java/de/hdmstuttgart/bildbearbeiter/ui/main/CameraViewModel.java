package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.Random;

import utilities.ImageFileHandler;

public class CameraViewModel extends ViewModel {

    private Uri imageUri;
    private ImageFileHandler capturedImageFileHandler;
    private ImageFileHandler saveImageFileHandler;
    private String capturedImageTmpFileName = "capturedImage";

    public CameraViewModel(ImageFileHandler capturedImageFileHandler, ImageFileHandler saveImageFileHandler) {
        this.capturedImageFileHandler = capturedImageFileHandler;
        this.saveImageFileHandler = saveImageFileHandler;
    }

    public boolean saveImageToLibrary(Bitmap imageToSave) {
        Random r = new Random();
        return saveImageFileHandler.saveImage(imageToSave, "captured_" + r.nextInt());
    }

    public ImageFileHandler getCapturedImageFileHandler() {
        return capturedImageFileHandler;
    }

    public File createCapturedImageFile() {
        return capturedImageFileHandler.createFileWithName(capturedImageTmpFileName);
    }

    public Bitmap getCapturedBitmap() {
        return capturedImageFileHandler.getImage(capturedImageTmpFileName);
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return this.imageUri;
    }
}
