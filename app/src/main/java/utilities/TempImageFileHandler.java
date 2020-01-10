package utilities;

import java.io.File;

public class TempImageFileHandler extends ImageFileHandler {

    public TempImageFileHandler(File applicationFilesDir) {
        super(
                applicationFilesDir,
                new File(applicationFilesDir, "tmp")
        );
    }
}
