package de.hdmstuttgart.bildbearbeiter.ui.main;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.Random;

import de.hdmstuttgart.bildbearbeiter.Camera;

public class CameraViewModel extends ViewModel {

    private Camera model;

    public CameraViewModel(File appFilesDir) {
        model = new Camera(appFilesDir);
    }

    public boolean saveImageToLibrary(Bitmap imageToSave) {
        Random r = new Random();
        return model.saveImageToLibrary(imageToSave, "captured_" + r.nextInt());
    }

    public File createCapturedImageFile() {
        return model.createCapturedImageFile();
    }

    public Bitmap getCapturedBitmap() {
        return model.getCapturedBitmap();
    }

    public void setImageUri(Uri imageUri) {
        model.setImageUri(imageUri);
    }

    public Uri getImageUri() {
        return model.getImageUri();
    }
}
