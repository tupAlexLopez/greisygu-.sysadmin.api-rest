package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void init() throws IOException;

    Resource search(String filename);

    String upload(MultipartFile file);

    boolean delete(String filename);

    String update( String filename, MultipartFile newFile );
}
