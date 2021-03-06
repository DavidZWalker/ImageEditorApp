package de.hdmstuttgart.bildbearbeiter.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

/**
 * This is the Camera View, it contains UI operations and communicates with the ViewModel.
 */
public class CameraFragment extends Fragment {

    private Button saveImageButton;
    private ImageView capturedImageView;
    private CameraViewModel viewModel;
    private ViewPager viewPagerParent;
    private final String logTag = "CameraFragment";

    /*
     * Inflates the CameraFragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(logTag, "Inflating CameraFragment view...");
        return inflater.inflate(R.layout.camera_fragment, container, false);
    }

    /*
     * Creates the viewModel and assigns the Buttons and their onclick listeners.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new CameraViewModel(getActivity().getFilesDir());

        capturedImageView = getActivity().findViewById(R.id.capturedImage);
        saveImageButton = getActivity().findViewById(R.id.saveImageButton);
        saveImageButton.setOnClickListener(v -> saveImageToLibrary());
        saveImageButton.setVisibility(View.INVISIBLE);
        Log.d(logTag, "CameraFragment created");
    }

    /**
     * Saves the captured image to the internal library
     */
    private void saveImageToLibrary() {
        Log.d(logTag, "Attempting to save image to library...");
        Bitmap bmp = ((BitmapDrawable) capturedImageView.getDrawable()).getBitmap();
        new SaveImageTask(bmp).execute();
    }

    /**
     * Sets the parent view pager
     * @param viewPager the view pager
     */
    public void setViewPager(ViewPager viewPager) {
        viewPagerParent = viewPager;
    }

    /**
     * Reacts to the user taking a photo with the camera.
     */
    public void takePhoto() {
        Log.d(logTag, "Starting camera...");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String auth = getActivity().getApplicationContext().getPackageName() + ".provider";
        viewModel.setImageUri(FileProvider.getUriForFile(getContext(), auth, viewModel.createCapturedImageFile()));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, viewModel.getImageUri());
        intent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Log.d(logTag, "Intent prepared. Starting activity...");
        startActivityForResult(intent, 100);
    }

    /**
     * Called when the Camera activity returns with a result
     * @param requestCode the request code made to the activity
     * @param resultCode the response code from the activity
     * @param data the intent data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Log.d(logTag, "Picture taken. Displaying image...");
            loadImageIntoView();
        } else if (resultCode == 0) {
            Log.d(logTag, "User cancelled. Switching to library...");
            switchToLibrary();
        }
    }

    /**
     * Loads the captured image into the view
     */
    private void loadImageIntoView() {
        Log.d(logTag, "Attempting to load image into view...");
        Uri selectedImage = viewModel.getImageUri();
        getActivity().getContentResolver().notifyChange(selectedImage, null);
        Bitmap bmp = viewModel.getCapturedBitmap();
        if (bmp != null) {
            capturedImageView.setImageBitmap(bmp);
            saveImageButton.setVisibility(View.VISIBLE);
        } else UIUtil.showShortSnackbar(getView(), "Failed to get image.");
    }

    /**
     * Switches to the library page
     */
    private void switchToLibrary() {
        viewPagerParent.setCurrentItem(MainActivity.LIBRARY_PAGE - 1);
    }

    /**
     * Asynchronous task for saving the image to the internal library
     */
    private class SaveImageTask extends AsyncTask<Void, Void, Boolean> {

        private Bitmap imageToSave;

        /**
         * This {@link AsyncTask} is responsible for saving the image to the internal library.
         *
         * @param imageToSave the image to save
         */
        public SaveImageTask(Bitmap imageToSave) {
            this.imageToSave = imageToSave;
        }

        @Override
        protected void onPreExecute() {
            Log.d(logTag, "Starting async task for saving...");
            saveImageButton.setText(R.string.savingText);
            saveImageButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d(logTag, "Doing background task...");
            return viewModel.saveImageToLibrary(imageToSave);
        }

        @Override
        protected void onPostExecute(Boolean saveResult) {
            super.onPostExecute(saveResult);
            Log.d(logTag, "Background task complete! Finalizing...");
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
