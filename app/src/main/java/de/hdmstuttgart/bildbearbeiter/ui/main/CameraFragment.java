package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.Constants;
import utilities.ImageFileHandler;

public class CameraFragment extends Fragment {

    private Button takePhotoButton;
    private Button saveImageButton;
    private ImageView capturedImageView;
    private Uri imageUri;
    private ImageFileHandler imageFileHandler;
    private Bitmap capturedBitmap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        capturedImageView = getActivity().findViewById(R.id.capturedImage);
        saveImageButton = getActivity().findViewById(R.id.saveImageButton);
        saveImageButton.setOnClickListener(v -> saveImageToLibrary());
        saveImageButton.setVisibility(View.INVISIBLE);
        takePhotoButton = getActivity().findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(v -> takePhoto());
    }

    private void saveImageToLibrary() {
        ImageFileHandler ifh = new ImageFileHandler(getContext().getFilesDir(), Constants.IMAGES_LIBRARY);
        Random r = new Random();
        try {
            ifh.saveImage(capturedBitmap, "captured_" + r.nextInt());
            saveImageButton.setVisibility(View.GONE);
            Snackbar.make(getView(), "Successfully saved!", Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void takePhoto() {
        imageFileHandler = new ImageFileHandler(getContext().getFilesDir(), "capturedImages");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String auth = getActivity().getApplicationContext().getPackageName() + ".provider";
        imageUri = FileProvider.getUriForFile(getContext(), auth, imageFileHandler.createFileWithName(Constants.IMAGES_TMP_CAM));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Uri selectedImage = imageUri;
            getActivity().getContentResolver().notifyChange(selectedImage, null);
            try {
                capturedBitmap = imageFileHandler.getImage(Constants.IMAGES_TMP_CAM);
                capturedImageView.setImageBitmap(capturedBitmap);
                saveImageButton.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                        .show();
                Log.e("Camera", e.toString());
            }
        }
        }
    }
