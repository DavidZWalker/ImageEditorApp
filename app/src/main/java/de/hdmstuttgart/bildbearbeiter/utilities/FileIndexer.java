package de.hdmstuttgart.bildbearbeiter.utilities;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileIndexer {
    private FileHandler fileHandler;
    private int index;
    private File internatIndexStore;

    public int getIndex(Context context) {
        index = readIndex(context);
        index++;
        saveIndex(context);
        return index;
    }

    private void saveIndex(Context context) {
        fileHandler = new FileHandler(context, Constants.TEXT_LIBRARY);
        try {
            fileHandler.saveToFile(Constants.INDEX_FILE_NAME, index);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int readIndex(Context context) {
        fileHandler = new FileHandler(context, Constants.TEXT_LIBRARY);
        try {
            index = fileHandler.readFromFile(Constants.INDEX_FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return index;
    }

}
