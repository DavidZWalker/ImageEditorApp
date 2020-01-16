package de.hdmstuttgart.bildbearbeiter.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The type Image file handler. It handles saving and retrieving images from the app storage and can also save images to gallery.
 */
public class ImageFileHandler {

    /**
     * The constant IMAGE_DIR_LIB contains the name of the directory which saves pictures consistently.
     */
    public static final String IMAGE_DIR_LIB = "BBImages";
    /**
     * The constant IMAGE_DIR_TMP contains the name of the directory in which pictures are saved temporarily.
     */
    public static final String IMAGE_DIR_TMP = "tmp";

    private File imageDir;

    /**
     * Instantiates a new Image file handler.
     *
     * @param applicationDir location of the application directory
     * @param imageSubDir    location of the images subdirectory
     */
    public ImageFileHandler(File applicationDir, String imageSubDir) {
        this.imageDir = new File(applicationDir, imageSubDir);
        if (!imageDir.exists()) imageDir.mkdirs();
    }

    /**
     * Saves the Image to the internal app storage.
     *
     * @param imageToSave the image to save
     * @param fileName    the file name
     * @throws IOException the io exception if saving was unsuccessful
     */
    public final void saveImage(Bitmap imageToSave, String fileName) throws IOException {
        File file = new File(imageDir, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        imageToSave.compress(Bitmap.CompressFormat.PNG, 0, fos);
        fos.close();
    }

    /**
     * Loads an image from Storage.
     *
     * @param imgName the image name of the image to be retrieved
     * @return the loaded image
     * @throws IOException the io exception if loading is unsuccessful
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
     * Deletes any file given via params.
     *
     * @param fileToRemove the file to remove
     */
    public void deleteFile(File fileToRemove) {
        fileToRemove.delete();
    }

    /**
     * Saves the provided image to Gallery
     *
     * @param bitmapToSave the bitmap to save to the gallery
     * @param context      the context
     * @return true if saved successful, false otherwise
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
