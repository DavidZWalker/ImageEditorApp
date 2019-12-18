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

import java.io.File;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.ImageFileHandler;

public class CameraFragment extends Fragment {

    private CameraViewModel mViewModel;
    private Button takePhotoButton;
    private ImageView capturedImageView;
    private Uri imageUri;
    private ImageFileHandler imageFileHandler;
    private String fileName = "test";

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        capturedImageView = getActivity().findViewById(R.id.capturedImage);
        takePhotoButton = getActivity().findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    public void takePhoto() {
        imageFileHandler = new ImageFileHandler(getContext(), "capturedImages");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String auth = getActivity().getApplicationContext().getPackageName() + ".provider";
        imageUri = FileProvider.getUriForFile(getContext(), auth, imageFileHandler.createFileWithName(fileName));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        Bitmap bmp = imageFileHandler.getImage(fileName);
                        capturedImageView.setImageBitmap(bmp);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }
