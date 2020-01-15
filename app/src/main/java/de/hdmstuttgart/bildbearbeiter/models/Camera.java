package de.hdmstuttgart.bildbearbeiter.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class Camera {
    private Uri imageUri;
    private ImageFileHandler capturedImageFileHandler;
    private ImageFileHandler saveImageFileHandler;
    private String capturedImageTmpFileName = "capturedImage";

    public Camera(File appFilesDir) {
        capturedImageFileHandler = new ImageFileHandler(appFilesDir, ImageFileHandler.IMAGE_DIR_TMP);
        saveImageFileHandler = new ImageFileHandler(appFilesDir, ImageFileHandler.IMAGE_DIR_LIB);
    }

    public void saveImageToLibrary(Bitmap imageToSave, String fileName) throws IOException {
        saveImageFileHandler.saveImage(imageToSave, fileName);
    }

    public File createCapturedImageFile() {
        return capturedImageFileHandler.createFileWithName(capturedImageTmpFileName);
    }

    public Bitmap getCapturedBitmap() throws IOException {
        return capturedImageFileHandler.getImage(capturedImageTmpFileName);
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return this.imageUri;
    }
}
