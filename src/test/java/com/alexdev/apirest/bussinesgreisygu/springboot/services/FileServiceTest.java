package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.services.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Servicio - Archivos")
public class FileServiceTest {
    @Autowired
    FileServiceImpl uploadService;

    static MockMultipartFile testFile;

    @BeforeAll
    static void init(){
        testFile = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image data".getBytes(StandardCharsets.UTF_8)
        );
    }

    @Test
    @DisplayName("Deberia guardar un archivo.")
    void testShouldToSaveAFileSuccessfully() {
        //Arrange
        MockMultipartFile file =testFile;

        //Act
        String resultUpload =uploadService.upload(file);

        //Assert
        assertNotNull( resultUpload );
        assertEquals( file.getOriginalFilename() , resultUpload );
    }

    @Test
    @DisplayName("Deberia buscar un archivo existente.")
    void testShouldSearchForAnExistingFileSuccessfully(){
        //Arrange
        MockMultipartFile fileToUpload =testFile;
        String savedFileToSearch =fileToUpload.getOriginalFilename();

        //Act
        uploadService.upload( fileToUpload );
        Resource resourceFound =uploadService.search( savedFileToSearch );

        //Assert
        assertTrue( resourceFound.exists() );
        assertTrue( resourceFound.isFile() );
    }

    @Test
    @DisplayName("Deberia modificar un archivo existente.")
    void testShouldToUpdateExistingFileSuccessfully(){
        //Arrange
        MockMultipartFile fileToUpload =testFile;
        MockMultipartFile newDataFile = new MockMultipartFile(
                "file",
                "image23.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "image data".getBytes(StandardCharsets.UTF_8)
        );

        //Act
        String filename =uploadService.upload( fileToUpload );
        String newFilename =uploadService.update( filename, newDataFile );

        //Assert
        assertNotNull( newFilename );
        assertEquals( newDataFile.getOriginalFilename(), newFilename );
    }

    @Test
    @DisplayName("Deberia eliminar un archivo existente.")
    void testShouldToDeleteExistingFileSuccessfully(){
        //Arrange
        MockMultipartFile fileToUpload =testFile;

        //Act
        String filenameFromFileToDelete =uploadService.upload( fileToUpload );
        boolean isDeletedFile =uploadService.delete( filenameFromFileToDelete );

        //Assert
        assertTrue( isDeletedFile );
    }
}
