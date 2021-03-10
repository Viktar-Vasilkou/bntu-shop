package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.FileService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.event.annotation.PrepareTestInstance;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceImplTest {

    @Autowired
    private FileService fileService;

    @BeforeClass
    public static void init() {
        String fileName = "image.jpg";
        File file = new File("/Users/77611/Pictures/Saved Pictures" + "/" + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteImage_existsFile_true() throws IOException {
        assertTrue(fileService.deleteFile("image.jpg"));
    }

    @Test
    public void deleteImage_Null_false() throws IOException {
        assertFalse(fileService.deleteFile(null));
    }

    @Test(expected = IOException.class)
    public void deleteImage_notExistingFileName_false() throws IOException {
        assertFalse(fileService.deleteFile("InvalidFileName"));
    }

    @Test
    public void saveImage_fileNameAndMultipartFile_void() throws IOException {
        String savedFileName = "image.jpg";
        MockMultipartFile multipartFile
                = new MockMultipartFile(
                "file",
                savedFileName,
                MediaType.IMAGE_PNG_VALUE,
                "Image".getBytes()
        );

        savedFileName = fileService.uploadFile(multipartFile);
        assertTrue(Files.exists(Paths.get("/Users/77611/Pictures/Saved Pictures" + "/" + savedFileName)));
        Files.delete(Paths.get("/Users/77611/Pictures/Saved Pictures" + "/" + savedFileName));
    }

    @Test(expected = NoSuchFileException.class)
    public void saveImage_fileNameAndMultipartFileWithInvalidValue_noSuchFileException() throws IOException {
        String savedFileName = "/image.jpg";
        MockMultipartFile multipartFile
                = new MockMultipartFile(
                "file",
                savedFileName,
                MediaType.IMAGE_PNG_VALUE,
                "Image".getBytes()
        );

        savedFileName = fileService.uploadFile(multipartFile);
        assertTrue(Files.exists(Paths.get("/Users/77611/Pictures/Saved Pictures" + "/" + savedFileName)));
        Files.delete(Paths.get("/Users/77611/Pictures/Saved Pictures" + "/" + savedFileName));
    }

    @Test
    public void saveImage_fileNullFile_emptyString() throws IOException {
        String savedFileName = "image.jpg";
        MockMultipartFile multipartFile = null;

        savedFileName = fileService.uploadFile(multipartFile);
        assertEquals(savedFileName, "");
    }
}