package de.hdmstuttgart.bildbearbeiter.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

import de.hdmstuttgart.bildbearbeiter.R;
import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;


public class BottomSheetFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private ImageView imageView;

    public BottomSheetFragment(ImageView imageView) {
        this.imageView = imageView;
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
        v.findViewById(R.id.llCancel).setOnClickListener(this::onClickCancel);

        return v;
    }


    public void onClickEdit(View view) {
        //todo change redundant code
        Intent intent = new Intent(view.getContext(), ImageEditorActivity.class);
        Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
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

    }

    public void onClickDeleteImage(View view) {

    }

    public void onClickCancel(View view) {

    }
}
