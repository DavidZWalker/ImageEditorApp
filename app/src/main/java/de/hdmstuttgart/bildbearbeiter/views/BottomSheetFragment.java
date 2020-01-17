package de.hdmstuttgart.bildbearbeiter.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.adapters.ImageAdapter;
import de.hdmstuttgart.bildbearbeiter.models.ImageLibrary;
import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;

/**
 * This View creates a Bottom sheet, which opens, when the user presses long on an image displayed in the {@link ImageLibraryFragment}
 */
public class BottomSheetFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private ImageView imageView;
    private ImageAdapter parent;
    private ImageLibrary imageLibrary;
    private final String logTag = "BottomSheetFragment";

    /**
     * Instantiates a new Bottom sheet fragment.
     *
     * @param parent       the parent
     * @param imageView    the image view
     * @param imageLibrary the image library
     */
    public BottomSheetFragment(ImageAdapter parent, ImageView imageView, ImageLibrary imageLibrary) {
        this.imageView = imageView;
        this.imageLibrary = imageLibrary;
        this.parent = parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     * Inflates the view and assigns the Buttons and their onclick listeners.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(logTag, "Inflating bottom sheet view...");
        View v = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);

        v.findViewById(R.id.llEdit).setOnClickListener(this::onClickEdit);
        v.findViewById(R.id.llSave).setOnClickListener(this::onClickSaveImage);
        v.findViewById(R.id.llDelete).setOnClickListener(this::onClickDeleteImage);
        return v;
    }

    /**
     * When the user presses Edit, a new {@link ImageEditorActivity } is opened.
     *
     * @param view the view
     */
    public void onClickEdit(View view) {
        Log.d(logTag, "User clicked on 'Edit'. Attempting to start ImageEditorActivity...");
        Intent intent = new Intent(view.getContext(), ImageEditorActivity.class);
        Bitmap bmp = UIUtil.extractBitmap(imageView);
        ImageFileHandler ifh = new ImageFileHandler(view.getContext().getFilesDir(), ImageFileHandler.IMAGE_DIR_TMP);
        try {
            ifh.saveImage(bmp, "tmpImage");
        } catch (IOException e) {
            Log.e(logTag, "Failed to save temp image.", e);
        }

        intent.putExtra("imageURI", "tmpImage");
        view.getContext().startActivity(intent);
        dismiss();
    }

    /**
     * When the user presses save, the picture calls to {@link UIUtil} to extract the image and saves it into to the gallery.
     *
     * @param view the view
     */
    public void onClickSaveImage(View view) {
        Log.d(logTag, "User clicked on 'Save'. Attempting to save image...");
        Bitmap bmpToSave = UIUtil.extractBitmap(imageView);
        MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bmpToSave, "PhotoMagicPhoto", "savedPhoto");
        Log.d(logTag, "Image saved successfully!");
        MainActivity.showSnackbar("Image saved!");
        dismiss();
    }

    /**
     * When the user presses delete, the file and the bitmap are deleted from the internal app storage.
     *
     * @param view the view
     */
    public void onClickDeleteImage(View view) {
        Log.d(logTag, "User clicked on 'Delete'. Attempting to remove image...");
        Bitmap bmp = UIUtil.extractBitmap(imageView);
        if (bmp != null) {
            imageLibrary.removeImage(bmp);
            parent.removeBitmap(bmp);
            MainActivity.showSnackbar("Image deleted!");
        }

        Log.d(logTag, "Image deleted successfully!");
        dismiss();
    }
}