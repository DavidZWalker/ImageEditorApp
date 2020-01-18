package de.hdmstuttgart.bildbearbeiter.views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.filters.IBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;
import de.hdmstuttgart.bildbearbeiter.viewmodels.ImageEditorViewModel;

/**
 * The activity in which a Bitmap is edited with different filters.
 */
public class ImageEditorActivity extends AppCompatActivity {

    /**
     * The Image view.
     */
    ImageView imageView;

    /**
     * The Filter buttons.
     */
    LinearLayout filterButtons;

    /**
     * The Save button.
     */
    Button saveButton;

    /**
     * The Selected filter view.
     */
    View selectedFilterView;

    /**
     * The View model.
     */
    ImageEditorViewModel viewModel;

    /**
     * The Running tasks.
     */
    List<AsyncTask> runningTasks;

    private final String logTag = "ImageEditorActivity";

    /*
     * Creates the viewModel and assigns the Buttons and their onclick listeners.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(logTag, "Creating view for ImageEditorActivity...");
        setContentView(R.layout.activity_fullscreen_image);
        viewModel = new ImageEditorViewModel(getFilesDir());

        imageView = findViewById(R.id.fullscreenImage);
        filterButtons = findViewById(R.id.filterButtons);
        saveButton = findViewById(R.id.btn_saveImage);
        runningTasks = new ArrayList<>();

        imageView.setImageBitmap(viewModel.getSourceImage());
        addFilterButtonsToView();
    }

    /**
     * Called after the activity has been created
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(logTag, "View created successfully");
    }

    /**
     * Called when the activity is stopped
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(logTag, "Stopping all running tasks...");
        runningTasks.forEach(x -> x.cancel(true));
        Log.d(logTag, "All running tasks stopped.");
    }

    /**
     * Saves an image from a view to the library.
     * @param v the view which contains the image
     */
    public void saveImageToLibrary(View v) {
        Log.d(logTag, "Attempting to save image to library...");
        Bitmap imageToSave = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        new SaveImageTask(imageToSave).execute();
    }

    /**
     * Adds the filter buttons to the view
     */
    private void addFilterButtonsToView() {
        for (IBitmapFilter f : viewModel.getAvailableFilters()) {
            Log.d(logTag, "Adding filter '" + f.getName() + "'...");
            new AddFilterButtonToViewTask(f).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    /**
     * Begins adding a filter button for the specified filter
     * @param filter the filter for the button
     * @return the view of the filter
     */
    private View startAddFilterButton(IBitmapFilter filter) {
        Log.d(logTag, "Initializing filter button for filter: " + filter.getName());
        RelativeLayout filterButtonLayoutRoot = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_button, null, false);
        ImageView thumb = (ImageView) filterButtonLayoutRoot.getChildAt(0);
        TextView textView = (TextView) filterButtonLayoutRoot.getChildAt(1);
        filterButtons.addView(filterButtonLayoutRoot);
        thumb.setImageBitmap(viewModel.createTempBlackBitmap());
        textView.setText(filter.getName());
        Log.d(logTag, "Filter button for " + filter.getName() + " initialized!");
        return filterButtonLayoutRoot;
    }

    /**
     * Finalizes filter button
     * @param view the view of the filter button
     * @param filteredBitmap the filtered bitmap to add to the view
     */
    private void finishAddFilterButton(View view, Bitmap filteredBitmap) {
        Log.d(logTag, "Finalizing filter button...");
        RelativeLayout filterButtonLayoutRoot = (RelativeLayout) view;
        ImageView thumb = (ImageView) filterButtonLayoutRoot.getChildAt(0);
        ProgressBar progressBar = (ProgressBar) filterButtonLayoutRoot.getChildAt(2);
        progressBar.setVisibility(View.GONE);
        thumb.setImageBitmap(filteredBitmap);
        filterButtonLayoutRoot.setOnClickListener(this::selectFilter);
        if (selectedFilterView == null)
            selectFilter(filterButtons.getChildAt(0));
        Log.d(logTag, "Filter button finalized!");
    }

    /**
     * Called when a filter is selected
     * @param view the view
     */
    private void selectFilter(View view) {
        Log.d(logTag, "User selected filter");
        // deselect old filter view
        if (selectedFilterView != null) {
            selectedFilterView.setBackgroundColor(Color.TRANSPARENT);
        }

        // select new filter view
        RelativeLayout newFilterView = (RelativeLayout) view;
        ImageView thumb = (ImageView) newFilterView.getChildAt(0);
        Bitmap bmp = ((BitmapDrawable) thumb.getDrawable()).getBitmap();
        imageView.setImageBitmap(bmp);
        newFilterView.setBackgroundColor(Color.GREEN);
        selectedFilterView = newFilterView;
    }

    /**
     * AsyncTask for adding a filter button to the view
     */
    private class AddFilterButtonToViewTask extends AsyncTask<Void, Void, Void> {

        /**
         * The Filtered bitmap.
         */
        Bitmap filteredBitmap;
        /**
         * The Filter.
         */
        IBitmapFilter filter;
        /**
         * The Layout.
         */
        View layout;

        /**
         * Instantiates a new Add filter button to view task.
         * @param filter the filter
         */
        public AddFilterButtonToViewTask(IBitmapFilter filter) {
            super();
            this.filter = filter;
        }

        @Override
        protected void onPreExecute() {
            Log.d(logTag, "Initializing async filter application...");
            super.onPreExecute();
            runningTasks.add(this);
            layout = startAddFilterButton(filter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(logTag, "Applying filter...");
            filteredBitmap = filter.applyFilter();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(logTag, "Filter applied");
            super.onPostExecute(aVoid);
            finishAddFilterButton(layout, filteredBitmap);
            runningTasks.remove(this);
        }
    }

    /*
     * An AsyncTask which Saves the images to the library.
     */
    private class SaveImageTask extends AsyncTask<Void, Void, Boolean> {

        private Bitmap imageToSave;

        /**
         * Instantiates a new Save image task.
         * @param imageToSave the image to save
         */
        public SaveImageTask(Bitmap imageToSave) {
            this.imageToSave = imageToSave;
        }

        @Override
        protected void onPreExecute() {
            Log.d(logTag, "Starting save async...");
            saveButton.setText(R.string.savingText);
            saveButton.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d(logTag, "Saving...");
            return viewModel.saveImageToLibrary(imageToSave);
        }

        @Override
        protected void onPostExecute(Boolean saveResult) {
            super.onPostExecute(saveResult);
            Log.d(logTag, "Saved!");
            saveButton.setEnabled(true);
            saveButton.setText(R.string.save);
            VibrationEffect.createOneShot(1000, 255);
            UIUtil.showShortSnackbar(findViewById(R.id.rootView), saveResult ? "Image saved to library!" : "Failed to save image.");
        }
    }
}
