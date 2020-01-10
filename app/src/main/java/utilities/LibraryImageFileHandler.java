package utilities;

import java.io.File;

public class LibraryImageFileHandler extends ImageFileHandler {

    public LibraryImageFileHandler(File applicationFilesDir) {
        super(
                new File(applicationFilesDir, "BBImages")
        );
    }
}
