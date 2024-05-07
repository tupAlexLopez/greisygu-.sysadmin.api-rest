package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.services.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MediaController.class)
public class MediaControllerTest {
    MockMultipartFile testFile;

    @Autowired
    MockMvc mvc;

    @MockBean
    FileService service;

    @BeforeEach
    void initEach(){
        testFile = new MockMultipartFile(
                "file",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image data".getBytes(StandardCharsets.UTF_8)
        );
    }

    @Test
    @DisplayName("Deberia subir un archivo correctamente")
    void testShouldUploadFileSuccessfully() throws Exception {
        //Arrange
        when( service.upload( testFile ) ).thenReturn(testFile.getOriginalFilename() );

        //Act
        ResultActions apiResponse =mvc.perform( multipart( "/media" )
                .file( testFile ));
        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.filename").exists() )
                .andExpect( jsonPath("$.url").exists() );
    }
    @Test
    @DisplayName("Deberia actualizar un archivo correctamente")
    void testShouldUpdateAnExistingFileSuccessfully() throws Exception {
        //Arrange
        MockMultipartFile newFile = new MockMultipartFile(
                "file",
                "image.jpg",
                MediaType.IMAGE_PNG_VALUE,
                "image data".getBytes(StandardCharsets.UTF_8)
        );

        when( service.update( testFile.getOriginalFilename(), newFile ) ).thenReturn(newFile.getOriginalFilename() );

        MockMultipartHttpServletRequestBuilder request =
                MockMvcRequestBuilders.multipart("/media/"+testFile.getOriginalFilename())
                        .file( newFile );

        request.with(req -> {
            req.setMethod("PUT");
            return req;
        });

        //Act
        ResultActions apiResponse =mvc.perform( request );

        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.filename").exists() )
                .andExpect( jsonPath("$.filename", is( newFile.getOriginalFilename() )) );
    }

    @Test
    @DisplayName("Deberia eliminar un archivo correctamente")
    void testShouldDeleteFileSuccessfully() throws Exception {
        //Arrange
        String filename =testFile.getOriginalFilename();
        when( service.delete( filename ) ).thenReturn( true );
        //Act
        ResultActions apiResponse =mvc.perform( delete( "/media/"+filename ) );

        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isNoContent() );
    }
}
