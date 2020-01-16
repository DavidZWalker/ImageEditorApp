package de.hdmstuttgart.bildbearbeiter.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Handles Files corresponding to the images.
 */
public class ImageFileHandler {

    /**
     * The constant IMAGE_DIR_LIB contains the location of the app storage.
     */
    public static final String IMAGE_DIR_LIB = "BBImages";
    /**
     * The constant IMAGE_DIR_TMP contains the location of the temporary app storage.
     */
    public static final String IMAGE_DIR_TMP = "tmp";

    private File imageDir;

    /**
     * Instantiates a new Image file handler and creates Directories if they don't exist already.
     *
     * @param applicationDir the application dir
     * @param imageSubDir    the image sub dir
     */
    public ImageFileHandler(@NonNull File applicationDir, String imageSubDir) {
        this.imageDir = new File(applicationDir, imageSubDir);
        if (!imageDir.exists()) imageDir.mkdirs();
    }

    /**
     * Saves a file with an {@link FileOutputStream}.
     *
     * @param imageToSave the image to save
     * @param fileName    the file name
     * @throws IOException the io exception
     */
    public final void saveImage(Bitmap imageToSave, String fileName) throws IOException {
        File file = new File(imageDir, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        imageToSave.compress(Bitmap.CompressFormat.PNG, 0, fos);
        fos.close();
    }

    /**
     * Loads an image from memory.
     *
     * @param imgName the img name
     * @return the image
     * @throws IOException the io exception
     */
    public final Bitmap getImage(String imgName) throws IOException {
        File imageFile = new File(imageDir, imgName);
        FileInputStream fis = new FileInputStream(imageFile);
        Bitmap decodedBitmap = BitmapFactory.decodeStream(fis);
        fis.close();
        return decodedBitmap;
    }

    /**
     * Gets image folder.
     *
     *
     * @return the image folder
     */
    public File getImageFolder() {
        return imageDir;
    }

    /**
     * Create file with name file.
     *
     * @param fileName the file name
     * @return the file
     */
    public File createFileWithName(String fileName) {
        return new File(imageDir, fileName);
    }

    /**
     * Delete file.
     *
     * @param fileToRemove the file to remove
     */
    public void deleteFile(File fileToRemove) {
        fileToRemove.delete();
    }
}
