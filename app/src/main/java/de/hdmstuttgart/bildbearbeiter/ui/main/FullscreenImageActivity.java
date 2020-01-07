package de.hdmstuttgart.bildbearbeiter.ui.main;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;

import de.hdmstuttgart.bildbearbeiter.filters.BlueBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.BrightBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.GreenBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.filters.IBitmapFilter;
import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.filters.NoBitmapFilter;
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
        filters.add(new BrightBitmapFilter(sourceBitmap));
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

    private Button createButtonForFilter(final IBitmapFilter filter)
    {
        Button filterButton = (Button) LayoutInflater.from(getApplicationContext()).inflate(R.layout.filter_button, null ,false);
        filterButton.setText(filter.getName());
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = filter.applyFilter();
                imageView.setImageBitmap(bmp);
            }
        });

        return filterButton;
    }
}
