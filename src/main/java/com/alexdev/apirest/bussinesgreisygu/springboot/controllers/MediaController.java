package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.response.MediaResponse;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final FileService uploadService;

    private final HttpServletRequest req;
    @Autowired
    public MediaController(FileService uploadService, HttpServletRequest req) {
        this.uploadService = uploadService;
        this.req = req;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MediaResponse saveImageProduct(@RequestParam("file") MultipartFile file) throws IOException {
        String filename =uploadService.upload( file );
        return MediaResponse.builder()
                .url( getUrlImage( filename ) )
                .filename( filename )
                .build();
    }

    @PutMapping("/{filename:.+}")
    public MediaResponse updateImageProduct(@PathVariable String filename, @RequestParam MultipartFile file ) {
        String newFilename =uploadService.update( filename, file );
        return MediaResponse.builder()
                .url( getUrlImage( newFilename ) )
                .filename( newFilename )
                .build();
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getMedia(@PathVariable String filename) throws IOException {
        Resource resource = uploadService.search( filename );
        String contentType =Files.probeContentType( resource.getFile().toPath() );

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body( resource );
    }

    @DeleteMapping("/{filename:.+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMedia(@PathVariable String filename) {
        uploadService.delete( filename );
    }



    private String getUrlImage( String filename ){
        String host =req.getRequestURL().toString().replace( req.getRequestURI(), "");

        return ServletUriComponentsBuilder
                .fromHttpUrl( host )
                .path("/media/")
                .path( filename )
                .toUriString();
    }
}
