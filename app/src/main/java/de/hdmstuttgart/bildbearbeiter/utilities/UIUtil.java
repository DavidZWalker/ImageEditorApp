package de.hdmstuttgart.bildbearbeiter.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import java.util.Objects;

public class UIUtil {

    public static void hideKeyboard(@NonNull Activity activeActivity) {
        if (activeActivity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activeActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(activeActivity.getCurrentFocus()).getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }
}
