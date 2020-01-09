package de.hdmstuttgart.bildbearbeiter.ui.main;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
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

import java.io.FileNotFoundException;

import de.hdmstuttgart.bildbearbeiter.filters.BlackWhiteBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.BlueBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.RedBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.SepiaBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.GreenBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.IBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.filters.NoBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.VignetteBitmapFilter;
import utilities.Constants;
import utilities.ImageFileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FullscreenImageActivity extends AppCompatActivity {

    ImageView imageView;
    LinearLayout filterButtons;
    Button saveButton;
    Bitmap sourceBitmap;
    List<IBitmapFilter> filters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        imageView = findViewById(R.id.fullscreenImage);
        filterButtons = findViewById(R.id.filterButtons);
        saveButton = findViewById(R.id.btn_saveImage);

        loadImage();
        loadFilterButtons();
    }

    private void initFilterList() {
        // ADD NEW FILTERS HERE!!!
         filters.add(new NoBitmapFilter(sourceBitmap));
         filters.add(new BlackWhiteBitmapFilter(sourceBitmap));
         filters.add(new SepiaBitmapFilter(sourceBitmap));
         filters.add(new VignetteBitmapFilter(sourceBitmap));
         filters.add(new RedBitmapFilter(sourceBitmap));
         filters.add(new GreenBitmapFilter(sourceBitmap));
         filters.add(new BlueBitmapFilter(sourceBitmap));
    }

    private void loadFilterButtons() {
        initFilterList();
        for (IBitmapFilter f : filters)
            new ApplyBitmapFilterTask(f).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void saveImageToLibrary(View v) {
        ImageFileHandler ifh = new ImageFileHandler(this.getApplicationContext(), Constants.IMAGES_LIBRARY);
        Random r = new Random();
        try {
            Bitmap imageToSave = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ifh.saveImage(imageToSave, "filtered_" + r.nextInt());
            Snackbar.make(findViewById(R.id.rootView), "Successfully saved!", Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImage()
    {
        try {
            ImageFileHandler ifh = new ImageFileHandler(this, Constants.IMAGES_TMP_FULLSCREEN);
            Bitmap bmp = ifh.getImage("tmpImage");
            imageView.setImageBitmap(bmp);
            sourceBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public View startAddFilterButton(IBitmapFilter filter) {
        RelativeLayout filterButtonLayoutRoot = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_button, null ,false);
        TextView textView = (TextView) filterButtonLayoutRoot.getChildAt(1);
        filterButtons.addView(filterButtonLayoutRoot);
        textView.setText(filter.getName());
        return filterButtonLayoutRoot;
    }

    public void finishAddFilterButton(View view, Bitmap filteredBitmap) {
        RelativeLayout filterButtonLayoutRoot = (RelativeLayout) view;
        final ImageView thumb = (ImageView) filterButtonLayoutRoot.getChildAt(0);
        ProgressBar progressBar = (ProgressBar) filterButtonLayoutRoot.getChildAt(2);
        progressBar.setVisibility(View.GONE);
        thumb.setImageBitmap(filteredBitmap);
        filterButtonLayoutRoot.setOnClickListener(v -> {
            Bitmap bmp = ((BitmapDrawable)thumb.getDrawable()).getBitmap();
            imageView.setImageBitmap(bmp);
        });
    }

    private class ApplyBitmapFilterTask extends AsyncTask<Void, Void, Void> {

        Bitmap filteredBitmap;
        IBitmapFilter filter;
        View layout;

        public ApplyBitmapFilterTask(IBitmapFilter filter)
        {
            super();
            this.filter = filter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
        }
    }
}
