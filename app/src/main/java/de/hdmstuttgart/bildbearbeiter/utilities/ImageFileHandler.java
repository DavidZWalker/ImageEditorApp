package de.hdmstuttgart.bildbearbeiter.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageFileHandler {

    public static final String IMAGE_DIR_LIB = "BBImages";
    public static final String IMAGE_DIR_TMP = "tmp";

    private File imageDir;

    public ImageFileHandler(File applicationDir, String imageSubDir) {
        this.imageDir = new File(applicationDir, imageSubDir);
        if (!imageDir.exists()) imageDir.mkdirs();
    }

    public final void saveImage(Bitmap imageToSave, String fileName) throws IOException {
        File file = new File(imageDir, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        imageToSave.compress(Bitmap.CompressFormat.PNG, 0, fos);
        fos.close();
    }

    public final Bitmap getImage(String imgName) throws IOException {
        File imageFile = new File(imageDir, imgName);
        FileInputStream fis = new FileInputStream(imageFile);
        Bitmap decodedBitmap = BitmapFactory.decodeStream(fis);
        fis.close();
        return decodedBitmap;
    }

    public File getImageFolder() {
        return imageDir;
    }

    public File createFileWithName(String fileName) {
        return new File(imageDir, fileName);
    }

    public void deleteFile(File fileToRemove) {
        fileToRemove.delete();
    }

    /*
     * This method saves a Provided File to the Gallery
     */
    public boolean saveToGallery(Bitmap bitmapToSave, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "UniqueFileName" + ".jpg");
        if (!file.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmapToSave.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                return true;
            } catch (java.io.IOException e) {
                e.printStackTrace();

            }

        }
        return false;
    }
}
