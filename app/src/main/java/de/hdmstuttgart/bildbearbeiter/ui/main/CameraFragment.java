package de.hdmstuttgart.bildbearbeiter.ui.main;

import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.function.Predicate;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.utilities.Constants;
import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class CameraFragment extends Fragment {

    private Button saveImageButton;
    private ImageView capturedImageView;
    private CameraViewModel viewModel;
    private ViewPager viewPagerParent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new CameraViewModel(
                new ImageFileHandler(getContext().getFilesDir(), ImageFileHandler.IMAGE_DIR_TMP),
                new ImageFileHandler(getContext().getFilesDir(), ImageFileHandler.IMAGE_DIR_LIB)
        );
        capturedImageView = getActivity().findViewById(R.id.capturedImage);
        saveImageButton = getActivity().findViewById(R.id.saveImageButton);
        saveImageButton.setOnClickListener(v -> saveImageToLibrary());
        saveImageButton.setVisibility(View.INVISIBLE);
    }

    private void saveImageToLibrary() {
        Bitmap bmp = ((BitmapDrawable)capturedImageView.getDrawable()).getBitmap();
        boolean saveResult = viewModel.saveImageToLibrary(bmp);
        String snackBarString;
        if (saveResult){
            saveImageButton.setVisibility(View.GONE);
            snackBarString = "Image saved to library!";
        }
        else snackBarString = "Failed to save image.";

        Snackbar.make(getView(),snackBarString, Snackbar.LENGTH_SHORT).show();
        capturedImageView.setImageBitmap(null);
        switchToLibrary();
    }

    public void setViewPagerContext(ViewPager viewPager) {
        viewPagerParent = viewPager;
    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String auth = getActivity().getApplicationContext().getPackageName() + ".provider";
        viewModel.setImageUri(FileProvider.getUriForFile(getContext(), auth, viewModel.createCapturedImageFile()));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, viewModel.getImageUri());
        intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            loadImageIntoView();
        }
        else if (resultCode == 0) {
            switchToLibrary();
        }
    }

    private void loadImageIntoView() {
        Uri selectedImage = viewModel.getImageUri();
        getActivity().getContentResolver().notifyChange(selectedImage, null);
        Bitmap bmp = viewModel.getCapturedBitmap();
        if (bmp != null) {
            capturedImageView.setImageBitmap(bmp);
            saveImageButton.setVisibility(View.VISIBLE);
        } else
            Snackbar.make(getView(), "Failed to get image.", Snackbar.LENGTH_SHORT).show();
    }

    private void switchToLibrary() {
        viewPagerParent.setCurrentItem(Constants.LIBRARY_PAGE - 1);
    }
}
