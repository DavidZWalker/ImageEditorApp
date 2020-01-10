package utilities;

import android.graphics.Bitmap;

import java.io.File;

public class LibraryImageFileHandler extends ImageFileHandler {

    public LibraryImageFileHandler(File applicationFilesDir) {
        super(
                applicationFilesDir,
                new File(applicationFilesDir, "BBImages")
        );
    }
}
