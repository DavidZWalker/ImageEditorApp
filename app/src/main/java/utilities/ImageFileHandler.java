package utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFileHandler {

    private Context applicationContext;
    private File internalImageStorage;

    public ImageFileHandler(Context context, String imageFolder)
    {
        applicationContext = context;
        internalImageStorage = new File(applicationContext.getFilesDir(), imageFolder);
        if (!internalImageStorage.exists()) internalImageStorage.mkdirs();
    }

    public void saveImage(@NonNull Bitmap bmp, String title) throws IOException {
        File file = new File(internalImageStorage, title);
        FileOutputStream fos = new FileOutputStream(file);
        bmp.compress(Bitmap.CompressFormat.JPEG, Bitmap.DENSITY_NONE, fos);
        fos.close();
    }

    public Bitmap getImage(String imgName) throws FileNotFoundException {
            File imageFile = new File(internalImageStorage, imgName);
            FileInputStream fis = new FileInputStream(imageFile);
            return BitmapFactory.decodeStream(fis);
    }

    public Bitmap getBitmapFromResource(int resId) {
        return BitmapFactory.decodeResource(applicationContext.getResources(), resId);
    }

    public File getImageFolder() {
        return internalImageStorage;
    }
}
