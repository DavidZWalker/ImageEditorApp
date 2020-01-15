package de.hdmstuttgart.bildbearbeiter.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;
import de.hdmstuttgart.bildbearbeiter.viewmodels.CameraViewModel;

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
        viewModel = new CameraViewModel(getActivity().getFilesDir());

        capturedImageView = getActivity().findViewById(R.id.capturedImage);
        saveImageButton = getActivity().findViewById(R.id.saveImageButton);
        saveImageButton.setOnClickListener(v -> saveImageToLibrary());
        saveImageButton.setVisibility(View.INVISIBLE);
    }

    private void saveImageToLibrary() {
        Bitmap bmp = ((BitmapDrawable) capturedImageView.getDrawable()).getBitmap();
        new SaveImageTask(bmp).execute();
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
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            loadImageIntoView();
        } else if (resultCode == 0) {
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
        } else UIUtil.showShortSnackbar(getView(), "Failed to get image.");
    }

    private void switchToLibrary() {
        viewPagerParent.setCurrentItem(MainActivity.LIBRARY_PAGE - 1);
    }

    private class SaveImageTask extends AsyncTask<Void, Void, Boolean> {

        private Bitmap imageToSave;

        public SaveImageTask(Bitmap imageToSave) {
            this.imageToSave = imageToSave;
        }

        @Override
        protected void onPreExecute() {
            saveImageButton.setText(R.string.savingText);
            saveImageButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return viewModel.saveImageToLibrary(imageToSave);
        }

        @Override
        protected void onPostExecute(Boolean saveResult) {
            super.onPostExecute(saveResult);
            String saveText;
            if (saveResult) {
                capturedImageView.setImageBitmap(null);
                saveText = "Image saved to library!";
            } else saveText = "Failed to save image.";

            saveImageButton.setEnabled(true);
            saveImageButton.setText(R.string.save);
            saveImageButton.setVisibility(View.INVISIBLE);
            UIUtil.showShortSnackbar(getActivity().findViewById(android.R.id.content), saveText);

            if (saveResult)
                switchToLibrary();
        }
    }
}
