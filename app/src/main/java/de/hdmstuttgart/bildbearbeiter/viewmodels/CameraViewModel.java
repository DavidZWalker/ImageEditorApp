package de.hdmstuttgart.bildbearbeiter.viewmodels;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import de.hdmstuttgart.bildbearbeiter.models.Camera;

public class CameraViewModel extends ViewModel {

    private Camera model;

    public CameraViewModel(File appFilesDir) {
        model = new Camera(appFilesDir);
    }

    public boolean saveImageToLibrary(Bitmap imageToSave) {
        try {
            Random r = new Random();
            model.saveImageToLibrary(imageToSave, "captured_" + r.nextInt());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public File createCapturedImageFile() {
        return model.createCapturedImageFile();
    }

    public Bitmap getCapturedBitmap() {
        try {
            return model.getCapturedBitmap();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setImageUri(Uri imageUri) {
        model.setImageUri(imageUri);
    }

    public Uri getImageUri() {
        return model.getImageUri();
    }
}
