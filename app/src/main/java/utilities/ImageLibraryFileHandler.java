package utilities;

import android.graphics.Bitmap;

import java.io.File;

public class ImageLibraryFileHandler extends ImageFileHandler {

    public ImageLibraryFileHandler(File applicationFilesDir) {
        super(
                applicationFilesDir,
                new File(applicationFilesDir, "BBImages")
        );
    }
}
