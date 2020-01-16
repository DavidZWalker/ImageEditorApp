package de.hdmstuttgart.bildbearbeiter.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

/**
 * This class provides various UI utilities.
 */
public class UIUtil {

    /**
     * Hides the Keyboard from the Activity.
     *
     * @param activeActivity the currently active activity
     */
    public static void hideKeyboard(@NonNull Activity activeActivity) {
        if (activeActivity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activeActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(activeActivity.getCurrentFocus()).getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }

    /**
     * Extracts the Bitmap from an ImageView
     *
     * @param iv the iv
     * @return the bitmap
     */
    public static Bitmap extractBitmap(ImageView iv) {
        if (iv != null) {
            BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
            return drawable != null ? drawable.getBitmap() : null;
        }

        return null;
    }

    /**
     * Shows a short Snackbar.
     *
     * @param view    the view
     * @param message the message
     */
    public static void showShortSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
