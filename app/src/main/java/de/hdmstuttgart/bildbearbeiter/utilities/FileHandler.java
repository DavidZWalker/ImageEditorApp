package de.hdmstuttgart.bildbearbeiter.utilities;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private FileWriter fileWriter;
    private Context applicationContext;
    private File internalTextStorage;

    public FileHandler(Context context, String textFolder) {
        this.applicationContext = context;
        internalTextStorage = new File(applicationContext.getFilesDir(), textFolder);
        if (!internalTextStorage.exists()) internalTextStorage.mkdirs();
    }

    public boolean saveToFile(String title, int content) throws IOException {
        File file = new File(internalTextStorage, title);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content);
        return true;
    }

    public int readFromFile(String title)throws FileNotFoundException {
        File textFile = new File(internalTextStorage, title);
        FileInputStream fileInputStream = new FileInputStream(textFile);
        Integer read = Integer.MIN_VALUE;
        try {
            read = fileInputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return read;
    }
}
