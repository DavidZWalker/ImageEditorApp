package de.hdmstuttgart.bildbearbeiter.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
        dismiss();
    }

    public void onClickSaveImage(View view) {
        Bitmap bmpToSave = UIUtil.extractBitmap(imageView);
        MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bmpToSave, "PhotoMagicPhoto" , "savedPhoto");
        MainActivity.showSnackbar("Image saved!");
        dismiss();
    }

    public void onClickDeleteImage(View view) {
        Bitmap bmp = UIUtil.extractBitmap(imageView);
        if (bmp != null) {
            imageLibrary.removeImage(bmp);
            parent.removeBitmap(bmp);
            MainActivity.showSnackbar("Image deleted!");
        }
        dismiss();
    }

}
