package de.hdmstuttgart.bildbearbeiter.ui.main;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import de.hdmstuttgart.bildbearbeiter.R;
import utilities.ImageFileHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        ImageView mContentView = findViewById(R.id.fullscreenImage);

        ImageFileHandler ifh = new ImageFileHandler(this, "tmp");
        Bitmap bmp = null;
        try {
            bmp = ifh.getImage("tmpImage");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mContentView.setImageBitmap(bmp);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
