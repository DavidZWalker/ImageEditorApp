package de.hdmstuttgart.bildbearbeiter.ui.main;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class FullscreenImageActivity extends AppCompatActivity {

    ImageView imageView;
    LinearLayout filterButtons;
    List<IBitmapFilter> filters = new ArrayList<>();
    Bitmap sourceBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        imageView = findViewById(R.id.fullscreenImage);
        filterButtons = findViewById(R.id.filterButtons);

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
        for (final IBitmapFilter f : filters) {

            filterButtons.addView(createButtonForFilter(f));
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
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

    private View createButtonForFilter(final IBitmapFilter filter)
    {
        RelativeLayout filterButtonLayoutRoot = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_button, null ,false);
        TextView textView = (TextView) filterButtonLayoutRoot.getChildAt(1);
        final ImageView thumb = (ImageView) filterButtonLayoutRoot.getChildAt(0);
        ProgressBar progressBar = (ProgressBar) filterButtonLayoutRoot.getChildAt(2);

        textView.setText(filter.getName());
        thumb.setImageBitmap(filter.applyFilter());
        filterButtonLayoutRoot.setOnClickListener(v -> {
            Bitmap bmp = ((BitmapDrawable)thumb.getDrawable()).getBitmap();
            imageView.setImageBitmap(bmp);
        });

        return filterButtonLayoutRoot;
    }

    private class AddFilterButtonTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
