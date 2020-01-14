package de.hdmstuttgart.bildbearbeiter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

import de.hdmstuttgart.bildbearbeiter.utilities.ImageFileHandler;

public class ImageFileHandlerTests {
    private static ImageFileHandler fileHandler;

   @BeforeClass
   public static void prepare()
   {
       File file = new File("photoMagicTestFiles");
       fileHandler = new ImageFileHandler(file, "imageFileHandlerTests");
   }

    @Test
    public void createImageHandler() {
        assertNotNull(fileHandler);
    }

    @Test
    public void getImageFolder_isCorrect() {
       File imageDir = fileHandler.getImageFolder();
       assertEquals("imageFileHandlerTests", imageDir.getName());
    }

    @Test
    public void createFileWithName_isCorrect() {
       String fileName = "fileName";
       File createdFile = fileHandler.createFileWithName(fileName);
       assertEquals(fileName, createdFile.getName());
    }
}
