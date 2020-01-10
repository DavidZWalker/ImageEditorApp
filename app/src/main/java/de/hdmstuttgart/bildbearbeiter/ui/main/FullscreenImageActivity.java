package de.hdmstuttgart.bildbearbeiter.ui.main;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bildbearbeiter.filters.IBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.R;

public class FullscreenImageActivity extends AppCompatActivity {

    ImageView imageView;
    LinearLayout filterButtons;
    Button saveButton;
    View selectedFilterView;
    FullscreenImageViewModel viewModel;
    List<AsyncTask> runningTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        viewModel = new FullscreenImageViewModel(getFilesDir());

        imageView = findViewById(R.id.fullscreenImage);
        filterButtons = findViewById(R.id.filterButtons);
        saveButton = findViewById(R.id.btn_saveImage);
        runningTasks = new ArrayList<>();

        imageView.setImageBitmap(viewModel.getSourceImage());
        addFilterButtonsToView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        runningTasks.forEach(x -> x.cancel(true));
    }

    public void saveImageToLibrary(View v) {
        Bitmap imageToSave = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        boolean saveResult = viewModel.saveImageToLibrary(imageToSave);
        Snackbar.make(findViewById(R.id.rootView),
                saveResult ? "Image saved to library!" : "Failed to save image.",
                Snackbar.LENGTH_SHORT).show();
    }

    private void addFilterButtonsToView() {
        for (IBitmapFilter f : viewModel.getAvailableFilters())
            new AddFilterButtonToView(f).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private View startAddFilterButton(IBitmapFilter filter) {
        RelativeLayout filterButtonLayoutRoot = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_button, null ,false);
        ImageView thumb = (ImageView) filterButtonLayoutRoot.getChildAt(0);
        TextView textView = (TextView) filterButtonLayoutRoot.getChildAt(1);
        filterButtons.addView(filterButtonLayoutRoot);
        thumb.setImageBitmap(viewModel.getTempBlackBitmap());
        textView.setText(filter.getName());
        if (filterButtons.getChildCount() == 1) selectFilter(filterButtonLayoutRoot);
        return filterButtonLayoutRoot;
    }

    private void finishAddFilterButton(View view, Bitmap filteredBitmap) {
        RelativeLayout filterButtonLayoutRoot = (RelativeLayout) view;
        ImageView thumb = (ImageView) filterButtonLayoutRoot.getChildAt(0);
        ProgressBar progressBar = (ProgressBar) filterButtonLayoutRoot.getChildAt(2);
        progressBar.setVisibility(View.GONE);
        thumb.setImageBitmap(filteredBitmap);
        filterButtonLayoutRoot.setOnClickListener(this::selectFilter);
    }

    private void selectFilter(View newSelection) {
        // deselect old filter view
        if (selectedFilterView != null) {
            selectedFilterView.setBackgroundColor(Color.TRANSPARENT);
        }

        // select new filter view
        RelativeLayout newFilterView = (RelativeLayout) newSelection;
        ImageView thumb = (ImageView) newFilterView.getChildAt(0);
        Bitmap bmp = ((BitmapDrawable)thumb.getDrawable()).getBitmap();
        imageView.setImageBitmap(bmp);
        newFilterView.setBackgroundColor(Color.GREEN);
        selectedFilterView = newFilterView;
    }

    private class AddFilterButtonToView extends AsyncTask<Void, Void, Void> {

        Bitmap filteredBitmap;
        IBitmapFilter filter;
        View layout;

        public AddFilterButtonToView(IBitmapFilter filter)
        {
            super();
            this.filter = filter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runningTasks.add(this);
            layout = startAddFilterButton(filter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            filteredBitmap = filter.applyFilter();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finishAddFilterButton(layout, filteredBitmap);
            runningTasks.remove(this);
        }
    }
}
