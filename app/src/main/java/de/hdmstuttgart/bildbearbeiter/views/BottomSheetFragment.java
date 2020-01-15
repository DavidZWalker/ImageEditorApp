package de.hdmstuttgart.bildbearbeiter.views;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.os.Environment;
import android.os.VibrationEffect;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.adapters.ImageAdapter;
import de.hdmstuttgart.bildbearbeiter.models.ImageLibrary;
import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;
import de.hdmstuttgart.bildbearbeiter.utilities.UIUtil;
import de.hdmstuttgart.bildbearbeiter.viewmodels.BottomSheetFramentViewModel;


public class BottomSheetFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private ImageView imageView;
    private ImageAdapter parent;
    private ImageLibrary imageLibrary;

    public BottomSheetFragment(ImageAdapter parent, ImageView imageView, ImageLibrary imageLibrary) {
        this.imageView = imageView;
        this.imageLibrary = imageLibrary;
        this.parent = parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);

        v.findViewById(R.id.llEdit).setOnClickListener(this::onClickEdit);
        v.findViewById(R.id.llSave).setOnClickListener(this::onClickSaveImage);
        v.findViewById(R.id.llDelete).setOnClickListener(this::onClickDeleteImage);
        return v;
    }


    public void onClickEdit(View view) {
        //todo change redundant code
        Intent intent = new Intent(view.getContext(), ImageEditorActivity.class);
        Bitmap bmp = UIUtil.extractBitmap(imageView);
        ImageFileHandler ifh = new ImageFileHandler(view.getContext().getFilesDir(), ImageFileHandler.IMAGE_DIR_TMP);
        try {
            ifh.saveImage(bmp, "tmpImage");
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra("imageURI", "tmpImage");
        view.getContext().startActivity(intent);
    }

    public void onClickSaveImage(View view) {

        saveToGallery(UIUtil.extractBitmap(imageView),getContext());
    }

    public void onClickDeleteImage(View view) {
        Bitmap bmp = UIUtil.extractBitmap(imageView);
        if (bmp != null) {
            imageLibrary.removeImage(bmp);
            parent.removeBitmap(bmp);
        }
    }
    /*
     * This method saves a Provided Bitmap to the Gallery
     */
    public void saveToGallery(Bitmap bitmapToSave, Context context) {

    }

}